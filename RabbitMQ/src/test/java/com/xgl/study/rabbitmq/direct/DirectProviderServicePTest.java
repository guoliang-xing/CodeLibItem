package com.xgl.study.rabbitmq.direct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xgl.study.rabbitmq.StartApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=StartApplication.class)
public class DirectProviderServicePTest {
	
	@Autowired
	private DirectProviderService sender;
	
	/**
	 * 测试消息队列
	 * @throws InterruptedException 
	 */
	@Test
	public void testSendMsg() throws InterruptedException {
		int flag = 0;
		while(true) {
			sender.sendInfoMsg("************************测试rabbitmq持久化 -----------："+flag++);
			Thread.sleep(2000);
		}
		//sender.sendErrorMsg("************************error xiao xi !");
		
	}

}