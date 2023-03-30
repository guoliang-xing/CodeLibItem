package com.xgl.study.rabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Fanout 交换器(广播-直接订阅一个交换器)
 * @author Liang
 *
 */
@Component
public class FanoutConsumerService {
	
	private Logger logger = LoggerFactory.getLogger(FanoutConsumerService.class);

	/**
	 * 短信服务
	 * @param msg
	 */
	@RabbitListener(
	    bindings = @QueueBinding(
	    	value = @Queue(value = "${mq.fanout.queue.sms}",autoDelete = "true"),
	    	exchange = @Exchange(name="${mq.fanout.exchange.fanout}",type = ExchangeTypes.FANOUT)
	    )	
	)
	public void receiverSms(String msg) {		
		logger.info("FanoutConsumerService.receiverSms 短信服务 : "+ msg);		
	}
	
	/**
	 * 邮件服务
	 * @param msg
	 */
	@RabbitListener(
	    bindings = @QueueBinding(
	    	value = @Queue(value = "${mq.fanout.queue.ema}",autoDelete = "true"),
	    	exchange = @Exchange(name="${mq.fanout.exchange.fanout}",type = ExchangeTypes.FANOUT)
	    )	
	)
	public void receiverEma(String msg) {		
		logger.info("FanoutConsumerService.receiverEma 邮件服务 : "+ msg);		
	}

}
