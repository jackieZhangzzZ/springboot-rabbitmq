package com.hand.rabbitmq.consumer;

import com.hand.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhuopeng.zhang@hand-china.com 2021/9/7 10:52
 * 消费者  基于插件的延迟消息
 */
@Slf4j
@Component
public class DelayQueueConsumer {

    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间:{}，收到的延迟队列消息：{}",new Date().toString(),msg);
    }
}
