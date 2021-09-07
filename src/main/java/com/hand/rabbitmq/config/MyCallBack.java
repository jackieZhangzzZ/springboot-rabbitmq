package com.hand.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhuopeng.zhang@hand-china.com 2021/9/7 14:50
 *
 * RabbitTemplate.ConfirmCallback交换机回调接口
 * RabbitTemplate.ReturnsCallback回退接口
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    //注入
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        //注入
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    /**
     * 交换机确认回调方法
     * 1.发消息交换机收到了
     * @param correlationData 回调的消息的ID及相关信息
     * @param b  交换机收到的消息 ack=true
     * @param s  null
     * 2.发消息 交换机接收失败了  回调
     *   correlationData 回调的消息的ID及相关信息
     *   b  交换机收到的消息 ack=false
     *   s   失败的原因  cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(b){
            log.info("交换机已经收到Id为：{}的消息",id);
        }else{
            log.info("交换机还未收到Id为：{}的消息，原因是：{}",id,s);
        }
    }

    //在当消息传递过程中不可达目的地时将消息返回给生产者。
    //只有不可达目的地的时候 才进行回退
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息：{}，被交换机{}退回，退回原因：{}，路由key:{}",
                new String(returnedMessage.getMessage().getBody()),
                returnedMessage.getExchange(),returnedMessage.getReplyText(),
                returnedMessage.getRoutingKey());
    }
}
