package com.xgl.study.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "GROUP", topic = "TOPIC",selectorExpression="TAG")
public class BootPushConsumer  implements RocketMQListener {
    @Override
    public void onMessage(Object message) {

    }
}
