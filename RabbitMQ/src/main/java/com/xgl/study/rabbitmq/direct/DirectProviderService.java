package com.xgl.study.rabbitmq.direct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DirectProviderService {
	
	private Logger logger = LoggerFactory.getLogger(DirectProviderService.class);
	
	@Autowired
	private AmqpTemplate rabbitmq;
	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${mq.direct.exchange.direct}")
	private String exchange;
	
	@Value("${mq.direct.routing-key.info}")
	private String routingKeyInfo;
	
	@Value("${mq.direct.routing-key.error}")
	private String routingKeyError;
	
	public void sendInfoMsg(String msg) {
		logger.info("ProviderService.sendInfoMsg :" + msg);
		rabbitTemplate.convertAndSend(exchange, routingKeyInfo, msg);
	}
	
	public void sendErrorMsg(String msg) {
		logger.info("ProviderService.sendErrorMsg :" + msg);
		rabbitmq.convertAndSend(exchange, routingKeyError, msg);
	}


}
