package com.xgl.study.rabbitmq.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Topic 交换器( 主题，规则匹配)
 * @author Liang
 *
 */
@Component
public class TopicConsumerService2 {
	
	private Logger logger = LoggerFactory.getLogger(TopicConsumerService2.class);

	/**
	 * 订单日志
	 * @param msg
	 */
	@RabbitListener(
	    bindings = @QueueBinding(
	    	value = @Queue(value = "${mq.topic.queue.order}",autoDelete = "true"),
	    	exchange = @Exchange(name="${mq.topic.exchange.topic}",type = ExchangeTypes.TOPIC),
	    	key="order.*.log"
	    )	
	)
	public void receiverOrderMsg(String msg) {		
		logger.info("TopicConsumerService.receiverOrderMsg 订单日志 : "+ msg);		
	}
	
	/**
	 * 商品日志
	 * @param msg
	 */
	@RabbitListener(
	    bindings = @QueueBinding(
	    	value = @Queue(value = "${mq.topic.queue.good}",autoDelete = "true"),
	    	exchange = @Exchange(value="${mq.topic.exchange.topic}",type = ExchangeTypes.TOPIC),
	    	key="good.*.log"
	    )	
	)
	public void receiverGoodMsg(String msg) {		
		logger.info("TopicConsumerService.receiverGoodMsg 商品日志 : "+ msg);		
	}
	
	/**
	 * 所有日志
	 * @param msg
	 */
	@RabbitListener(
	    bindings = @QueueBinding(
	    	value = @Queue(value = "${mq.topic.queue.all}",autoDelete = "true"),
	    	exchange = @Exchange(name="${mq.topic.exchange.topic}",type = ExchangeTypes.TOPIC),
	    	key="*.*.log"
	    )	
	)
	public void receiverAllMsg(String msg) {		
		logger.info("TopicConsumerService.receiverAllMsg 所有日志: "+ msg);		
	}

}
