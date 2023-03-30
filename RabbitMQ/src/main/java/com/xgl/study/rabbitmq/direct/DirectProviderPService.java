package com.xgl.study.rabbitmq.direct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DirectProviderPService {
	
	private Logger logger = LoggerFactory.getLogger(DirectProviderPService.class);
	
	@Autowired
	private AmqpTemplate rabbitmq;
	
	@Value("${mq.direct.exchange.directp}")
	private String exchange;
	
	@Value("${mq.direct.routing-key.infop}")
	private String routingKeyInfo;
	
	@Value("${mq.direct.routing-key.errorp}")
	private String routingKeyError;
	
	public void sendInfoMsg(String msg) {
		logger.info("DirectProviderPService.sendInfoMsg :" + msg);
		rabbitmq.convertAndSend(exchange, routingKeyInfo, msg);
	}
	
	public void sendErrorMsg(String msg) {
		logger.info("DirectProviderPService.sendErrorMsg :" + msg);
		rabbitmq.convertAndSend(exchange, routingKeyError, msg);
	}


}
