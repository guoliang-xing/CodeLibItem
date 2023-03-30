package com.xgl.study.rabbitmq.topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.xgl.study.rabbitmq.StartApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=StartApplication.class)
public class TopicProviderServiceTest {
	
	@Autowired
	private TopicProviderService sender;
	
	/**
	 * 测试消息队列
	 * @throws InterruptedException 
	 */
	@Test
	public void testSendMsg() throws InterruptedException {
		sender.sendOrderInfoMsg("************************** 发送订单 info 日志 **************************");
		sender.sendOrderErrorMsg("************************** 发送订单 error 日志 **************************");
		sender.sendGoodInfoMsg("************************** 发送商品 info 日志 **************************");
		sender.sendGoodErrorMsg("************************** 发送商品 error 日志 **************************");
		Thread.sleep(100);
	}

}