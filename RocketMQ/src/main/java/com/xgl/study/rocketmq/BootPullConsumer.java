package com.xgl.study.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "GROUP", topic = "TOPIC",selectorExpression="TAG")
public class BootPullConsumer implements RocketMQListener, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(Object message) {

    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {

    }
}
