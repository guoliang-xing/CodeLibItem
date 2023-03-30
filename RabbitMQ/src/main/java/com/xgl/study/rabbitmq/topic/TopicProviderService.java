package com.xgl.study.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//例子中路由键直接写死（规范中需要配置文件配置）
@Component
public class TopicProviderService {
	
	private Logger logger = LoggerFactory.getLogger(TopicProviderService.class);
	
	@Autowired
	private AmqpTemplate rabbitmq;
	
	@Value("${mq.topic.exchange.topic}")
	private String exchange;
	
	/**
	 * 发送订单info日志
	 * @param msg
	 */
	public void sendOrderInfoMsg(String msg) {
		logger.info("TopicProviderService.sendOrderInfoMsg :" + msg);
		rabbitmq.convertAndSend(exchange, "order.info.log", msg);
	}
	
	/**
	 * 发送订单error日志
	 * @param msg
	 */
	public void sendOrderErrorMsg(String msg) {
		logger.info("TopicProviderService.sendOrderErrorMsg :" + msg);
		rabbitmq.convertAndSend(exchange, "order.error.log", msg);
	}
	
	/**
	 * 发送商品info日志
	 * @param msg
	 */
	public void sendGoodInfoMsg(String msg) {
		logger.info("TopicProviderService.sendGoodInfoMsg :" + msg);
		rabbitmq.convertAndSend(exchange, "good.info.log", msg);
	}
	
	/**
	 * 发送商品error日志
	 * @param msg
	 */
	public void sendGoodErrorMsg(String msg) {
		logger.info("TopicProviderService.sendGoodErrorMsg :" + msg);
		rabbitmq.convertAndSend(exchange, "good.error.log", msg);
	}

}
