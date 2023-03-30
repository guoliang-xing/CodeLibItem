package com.xgl.study.rabbitmq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqReceiver {
	
	Logger logger = LoggerFactory.getLogger(RabbitmqReceiver.class); 
	
	@RabbitListener(queues = "hello-queue" )
	public void receiveMsg(String msg) {		
		logger.info("RabbitmqReceiver receive msg : " + msg);		
		throw new RuntimeException();
	}

}
