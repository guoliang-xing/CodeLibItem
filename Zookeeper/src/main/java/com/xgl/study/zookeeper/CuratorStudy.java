package com.xgl.study.zookeeper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorStudy {
	
	private static int count = 0;
	
	public static void main(String[] args) throws Exception {
		String connectString = "127.0.0.1:2181";
		CuratorFramework client = getCuratorFramework1(connectString);
		client.start();
		/*
		 * System.out.println(checkExists(client,"/my"));
		 * System.out.println(setData(client,"/my","修改值"));
		 * System.out.println(getData(client,"/my")); List<String> children =
		 * getChildren(client,"/my"); for(String child : children) {
		 * System.out.println(child); }
		 */
		distributedLock(client,"/metux");
//		Thread.sleep(1);
		/*
		 * client.start(); client.create().forPath("/my", "Test".getBytes());
		 */
	}
	
	/**
	 * 创建节点
	 * @param client
	 * @param path
	 * @param data
	 * @throws Exception
	 */
	public static void createNode(CuratorFramework client,String path,String data) throws Exception {
		client.create()
		   .creatingParentsIfNeeded()
		   .withMode(CreateMode.PERSISTENT)//PERSISTENT 永久节点  PERSISTENT_SEQUENTIAL 永久顺序节点  EPHEMERAL 临时节点  EPHEMERAL_SEQUENTIAL 临时顺序节点
		   .forPath(path,data.getBytes());
		client.close();
	}
	
	/**
	 * 判断死否存在
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean checkExists(CuratorFramework client,String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return stat != null;
	}
	
	/**
	 * 获取节点数据
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getData(CuratorFramework client,String path) throws Exception {
		byte[] data = client.getData().forPath(path);
		return new String(data);
	}
	
	/**
	 * 获取孩子节点
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<String> getChildren(CuratorFramework client,String path) throws Exception {
		List<String> children = client.getChildren().forPath(path);
		return children;
	}
	
	/**
	 * 修改数据
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static boolean setData(CuratorFramework client,String path,String data) throws Exception {
		Stat children = client.setData().forPath(path,data.getBytes());
		return children != null;
	}
	
	/**
	 * 删除数据
	 * @param client
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static void delete(CuratorFramework client,String path) throws Exception {
		client.delete().forPath(path);
	}
	
	/**
	 * 获取CuratorFramework方法1
	 * @return
	 */
	public static CuratorFramework getCuratorFramework1(String connectString) {
		//重试策略   
		//ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries)
		//baseSleepTimeMs 重试时间单位（ms）  maxRetries最大重试次数
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		return CuratorFrameworkFactory.newClient(connectString, retryPolicy);	
	}
	
	/**
	 * 获取CuratorFramework方法1
	 * @return
	 */
	public static CuratorFramework getCuratorFramework2(String connectString) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		return CuratorFrameworkFactory.builder()
		    .connectString(connectString)
		    .retryPolicy(retryPolicy)
		    .connectionTimeoutMs(1000)
		    .sessionTimeoutMs(30*1000)
		    .build();	
	}
	
	public static void distributedLock(CuratorFramework client,String path) throws Exception {
		InterProcessMutex lock = new InterProcessMutex(client,path);
		for(int i=0;i<=20;i++) {
			new Thread(()->{
				try {
					if (lock.acquire(10, TimeUnit.HOURS)) {
						count++;
						System.out.println(Thread.currentThread()+":"+count);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						lock.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
	}
}
