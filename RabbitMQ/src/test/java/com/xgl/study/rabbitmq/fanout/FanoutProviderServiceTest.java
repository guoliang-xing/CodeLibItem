package com.xgl.study.rabbitmq.fanout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xgl.study.rabbitmq.StartApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=StartApplication.class)
public class FanoutProviderServiceTest {
	
	@Autowired
	private FanoutProviderService sender;
	
	/**
	 * 测试消息队列
	 * @throws InterruptedException 
	 */
	@Test
	public void testSendMsg() throws InterruptedException {
		sender.sendMessage("--------------发送广播消息");
		Thread.sleep(1000);
	}

}