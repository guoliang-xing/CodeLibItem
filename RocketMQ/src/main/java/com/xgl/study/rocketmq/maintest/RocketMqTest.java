package com.xgl.study.rocketmq.maintest;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

public class RocketMqTest {

    public static void main(String[] args) {
        try {
            new RocketMqTest().batchSend("BatchTopic");
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * RocketMQ消息批量发送案例
     * @param topic
     * @return
     * @throws MQClientException
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     */
    public boolean batchSend(String topic) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
        producer.setNamesrvAddr("192.168.137.24:9876");
        producer.start();
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic,"Tag","OrderID001","Hello world 1".getBytes()));
        messages.add(new Message(topic,"Tag","OrderID002","Hello world 2".getBytes()));
        messages.add(new Message(topic,"Tag","OrderID003","Hello world 3".getBytes()));
        SendResult result = producer.send(messages);
        System.out.println(result);
        producer.shutdown();
        return true;
    }
    /**
     * RocketMQ消息顺序发送案例(指定发送到哪个消息队列)
     * @param topic
     * @return
     * @throws MQClientException
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     */
    public boolean orderSend(String topic) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
        producer.setNamesrvAddr("192.168.137.24:9876");
        producer.start();
        Message message = new Message(topic,"Tag","OrderID003","Hello world 3".getBytes());
        SendResult result = producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                int id = (int) arg;
                return mqs.get(id%(mqs.size()));
            }
        },1);
        System.out.println(result);
        producer.shutdown();
        return true;
    }

}
