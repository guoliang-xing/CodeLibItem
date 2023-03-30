package com.xgl.study.rabbitmq.direct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Direct 交换器(发布与订阅完全匹配)
 * @author Liang
 *
 */
@Component
public class DirectConsumerService {
	
	private Logger logger = LoggerFactory.getLogger(DirectConsumerService.class);

	@RabbitListener(
		bindings = @QueueBinding(
			value = @Queue(value = "${mq.direct.queue.info}",autoDelete = "true"),
			exchange = @Exchange(value = "${mq.direct.exchange.direct}",type = ExchangeTypes.DIRECT),
			key = "${mq.direct.routing-key.info}"
		)
	)
	public void receiverInfoMsg(String msg) {		
		logger.info("ConsumerService.receiverInfoMsg : "+ msg);		
	}
	
	@RabbitListener(
		bindings = @QueueBinding(
			value = @Queue(value = "${mq.direct.queue.error}",autoDelete = "true"),
			exchange = @Exchange(value = "${mq.direct.exchange.direct}",type = ExchangeTypes.DIRECT),
			key = "${mq.direct.routing-key.error}"
		)
	)
	public void receiverErrorMsg(String msg) {		
		logger.info("ConsumerService.receiverErrorMsg : "+ msg);		
	}

}
 