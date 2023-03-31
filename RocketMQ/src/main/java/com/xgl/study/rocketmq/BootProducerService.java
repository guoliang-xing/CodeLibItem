package com.xgl.study.rocketmq;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BootProducerService {

    private static final Logger logger = LoggerFactory.getLogger(BootProducerService.class);

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送单向消息
     * @param topic
     * @param msg
     */
    public void sendOneWay(String topic,String msg){
        logger.info("******发送单向消息");
        rocketMQTemplate.sendOneWay(topic,msg);
    }

    /**
     * 发送同步消息
     * @param topic
     * @param msg
     * @return
     */
    public SendResult syncSend(String topic,String msg){
        logger.info("******发送同步消息");
        return rocketMQTemplate.syncSend(topic,msg);
    }

    /**
     * 发送异步消息
     * @param topic
     * @param msg
     */
    public void asyncSend(String topic,String msg){
        logger.info("******异步消息");
        rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("******异步消息：发送成功");
            }
            @Override
            public void onException(Throwable e) {
                logger.error("******异步消息：发送失败",e);
            }
        });
    }

    /**
     * 发送延时消息
     * @param topic
     * @param msg
     * @param timeout
     * @param delayLevel  延迟级别（1-16）
     * @return
     */
    public SendResult syncSend(String topic,String msg,long timeout,int delayLevel){
        logger.info("******发送延时消息");
        Message<?> message = MessageBuilder.withPayload(msg).build();
        return rocketMQTemplate.syncSend(topic,message,timeout,delayLevel);
    }

    /**
     * 发送顺序消息
     * @param topic
     * @param msg
     * @param key   决定发往哪个queue
     * @return
     */
    public SendResult syncSendOrder(String topic,String msg,String key){
        logger.info("******发送顺序消息");
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, org.apache.rocketmq.common.message.Message msg, Object arg) {
                int id = arg.hashCode();
                return mqs.get(id%(mqs.size()));
            }
        });
       return rocketMQTemplate.syncSendOrderly(topic,msg,key);
    }

    /**
     * 发送批量消息
     * @param topic
     * @param msgList
     * @return
     */
    public SendResult syncSend(String topic,List<String> msgList){
        logger.info("******发送批量消息");
        List<Message> list = new ArrayList<>();
        for(String msg : msgList){
            list.add(MessageBuilder.withPayload(msg).build());
        }
        return rocketMQTemplate.syncSend(topic,list);
    }

    /**
     * 发送带标签的消息
     * @param topic
     * @param tags
     * @param msg
     * @return
     */
    public SendResult syncSend(String topic,String[] tags, String msg){
        logger.info("******发送带tag消息");
        String destination = topic;
        for(int i=0;i<tags.length;i++){
            if(i==0){
                topic+=":"+tags[i];
            }else if(i==(tags.length-1)){
                topic+=tags[i];
            }else{
                topic+="||"+tags[i];
            }
        }
        return rocketMQTemplate.syncSend(topic,msg);
    }

    /**
     * 发送带属性的消息
     * @param topic
     * @param msg
     * @param headers 消息属性
     * @return
     */
    public void convertAndSend(String topic, String msg, Map<String,Object> headers){
        logger.info("******发送带属性的消息");
        rocketMQTemplate.convertAndSend(topic,msg,headers);
    }

    /**
     *
     * @param topic
     * @param msg
     */
    public void sendMessageInTransaction(String topic, String msg){
        logger.info("******发送事务消息");
        Message<?> message = MessageBuilder.withPayload(msg).build();
        //第三个参数对应监听方法中的第二个参数
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(topic,message,null);
        String sendResult = result.getSendStatus().name();
        String localTxStatus = result.getLocalTransactionState().name();
    }

    @RocketMQTransactionListener
    class TransactionListenerImpl implements RocketMQLocalTransactionListener{
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            RocketMQLocalTransactionState resultState = RocketMQLocalTransactionState.COMMIT;
            try {
                //业务处理

            }catch (Exception e){
                resultState = RocketMQLocalTransactionState.UNKNOWN;
            }
            return resultState;
        }
        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            RocketMQLocalTransactionState resultState = RocketMQLocalTransactionState.COMMIT;
            try{
                //查询事务是否提交成功
            }catch (Exception e){
                resultState  = RocketMQLocalTransactionState.ROLLBACK;
            }
            return resultState;
        }
    }

}
