package com.xgl.study.rabbitmq.direct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xgl.study.rabbitmq.StartApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=StartApplication.class)
public class DirectProviderServiceTest {
	
	@Autowired
	private DirectProviderService sender;
	
	/**
	 * 测试消息队列
	 * @throws InterruptedException 
	 */
	@Test
	public void testSendMsg() throws InterruptedException {
		sender.sendInfoMsg("************************info xiao xi !");
		sender.sendErrorMsg("************************error xiao xi !");
		Thread.sleep(1000);
	}

}