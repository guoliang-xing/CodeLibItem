package com.xgl.study.socket.nio.channel;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*  
 *  FileChannel专门操作文件的通道，用于文件的数据读写
 *    1.为阻塞式，不能设置为非阻塞式
 *    2.采用地址映射，实现零考拷贝，速度快
 *    3.能在文件指定位置进行读写操作
 *    4.为了避免数据丢失，强制立即将更新写入文件并存储
 *    
 */
public class FileChannelTest {
	
	
	public static void main(String[] args) throws Exception {
		
		//FileChannelTest.fastCopyFile("F:\\books\\SpringCloud\\疯狂Spring Cloud微服务架构实战.pdf", "doc/疯狂Spring Cloud微服务架构实战.pdf");
		FileChannelTest.copyFile("F:\\books\\SpringCloud\\疯狂Spring Cloud微服务架构实战.pdf", "doc/疯狂Spring Cloud微服务架构实战.pdf");
	}

	/**
	 *  单通道文件复制
	 */
	public static void copyFile(String originalFilePath,String destFilePath) {
		long beginTime= System.currentTimeMillis();
		FileInputStream fis = null;
		FileChannel cis = null;
		FileOutputStream fos = null;
		FileChannel cos = null;
		try {
			fis = new FileInputStream(originalFilePath);
			cis = fis.getChannel();
			fos = new FileOutputStream(destFilePath);
			cos = fos.getChannel();
			ByteBuffer buff = ByteBuffer.allocate(20*1024*1024);
			while (cis.read(buff) != -1) {
				buff.flip();
				cos.write(buff);
				buff.clear();
			}
			cos.force(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStream(fis,cis,fos,cos);
		}
		long endTime= System.currentTimeMillis();
		System.out.println(endTime-beginTime);
	}
	
	/**
	 * 采用多线程快速复制
	 * @param originalFilePath
	 * @param destFilePath
	 * @throws FileNotFoundException
	 */
	public static void fastCopyFile(String originalFilePath,String destFilePath) throws FileNotFoundException {		
		try {
			long beginTime= System.currentTimeMillis();
			//设置单词线程处理文件大小 20M
			long subFileSize = 10 * 1024 * 1024;
			//获取文件的大小并计算需要多少次线程处理
			File file = new File(originalFilePath);
			long fileSize = file.length();
			long time = fileSize % subFileSize == 0 ? fileSize/subFileSize : fileSize/subFileSize + 1 ;
			//使用线程池进行处理
			ExecutorService threadPool = Executors.newFixedThreadPool(5);
			CountDownLatch latch = new CountDownLatch((int) time);
			for(long i = 0 , position = 0 ;i < time ; i++ , position = i*subFileSize) {
				if(i == (time-1)) {
					//计算最后一次复制的数据长度
					subFileSize = fileSize - subFileSize * i;
				}
				threadPool.execute(
					new TransferFromThread(originalFilePath, destFilePath, position, subFileSize, latch)
			    );
			}
			//等待所有线程执行完
			latch.await();
			//关闭线程池
			threadPool.shutdown();
			long endTime= System.currentTimeMillis();
			System.out.println(endTime-beginTime);
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	/**
	 * transferTo(long position, long count, WritableByteChannel target)
	 *   position  源通道开始的读取位置
	 *   count  复制的数据长度
	 *   WritableByteChannel  复制的目的通道
	 */
	static class TransferToThread implements Runnable{
		private String inFile ;
		private String outFile;
		private long position;
		private long blockSize;
		private CountDownLatch latch;
		
		public TransferToThread(String inFile,String outFile, long position, long blockSize,CountDownLatch latch) {
			this.inFile = inFile;
			this.outFile = outFile;
			this.position = position;
			this.blockSize = blockSize;
			this.latch = latch;
		}

		@Override
		public void run() {
			FileChannel inChannel = null;
			FileChannel outChannel = null;
			RandomAccessFile ris = null;
			RandomAccessFile ros = null;
			try {
				ris = new RandomAccessFile(inFile,"rw");
				ros = new RandomAccessFile(outFile,"rw");
				inChannel = ris.getChannel();
				outChannel = ros.getChannel();
				outChannel.position(position);//设置读取到目的通道的位置
				inChannel.transferTo(position, blockSize, outChannel);
				latch.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeStream(outChannel,inChannel,ros,ris);
			}
		}
	}
	
	/**
	 * transferFrom(ReadableByteChannel src,long position, long count)
	 *   src 源文件通道
	 *   position  目的通道的开始位置
	 *   count    复制数据的长度
	 */
	static class TransferFromThread implements Runnable{
		private String inFile ;
		private String outFile;
		private long position;
		private long blockSize;
		private CountDownLatch latch;
		
		public TransferFromThread(String inFile,String outFile, long position, long blockSize,CountDownLatch latch) {
			this.inFile = inFile;
			this.outFile = outFile;
			this.position = position;
			this.blockSize = blockSize;
			this.latch = latch;
		}

		@Override
		public void run() {
			FileChannel inChannel = null;
			FileChannel outChannel = null;
			RandomAccessFile ris = null;
			RandomAccessFile ros = null;
			try {
				ris = new RandomAccessFile(inFile,"rw");
				ros = new RandomAccessFile(outFile,"rw");
				//从源文件复制时，不知道源文件大小，所以默认每一次就从0开始写入,  导致复制异常
				ros.setLength(ris.length());
				inChannel = ris.getChannel();
				outChannel = ros.getChannel();
				//指定从源通道的哪个位置开始读
				inChannel.position(position);
				outChannel.transferFrom(inChannel, position, blockSize);
				latch.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeStream(outChannel,inChannel,ros,ris);
			}
		}
	}
	
	//关闭流
    public static void closeStream(Closeable...closeables){
        for (Closeable closeable : closeables) {
            if (closeable != null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
