package com.xgl.study.rabbitmq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqSender {
	
	Logger logger = LoggerFactory.getLogger(RabbitmqSender.class); 
	
	@Autowired
	private AmqpTemplate rabbitAmqpTemplate;
	
	public void sendMsg(String msg) {
		logger.info(msg);
		rabbitAmqpTemplate.convertAndSend("hello-queue", msg);
		
	}

}
