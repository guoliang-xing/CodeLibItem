package com.xgl.study.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FanoutProviderService {
	
	private Logger logger = LoggerFactory.getLogger(FanoutProviderService.class);
	
	@Autowired
	private AmqpTemplate rabbitmq;
	
	@Value("${mq.fanout.exchange.fanout}")
	private String exchange;
	
	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(String msg) {
		logger.info("FanoutProviderService.sendMessage :" + msg);
		rabbitmq.convertAndSend(exchange, null, msg);
	}
	
}
