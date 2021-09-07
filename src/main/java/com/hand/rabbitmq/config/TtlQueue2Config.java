package com.hand.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuopeng.zhang@hand-china.com 2021/9/7 9:43
 *
 * 优化延时队列  新增了一个队列 QC  不设置TTL 时间
 */
@Configuration
public class TtlQueue2Config {
    //普通队列名称
    public static final String QUEUE_C = "QC";
    //死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    @Bean
    public Queue queueC(){
        Map<String, Object> arguments = new HashMap<>();
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }
    @Bean
    public Binding queueBindingX(@Qualifier("queueC") Queue queueC,
                                 @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
}
