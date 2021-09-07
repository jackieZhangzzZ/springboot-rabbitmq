package com.hand.rabbitmq.controller;

import com.hand.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuopeng.zhang@hand-china.com 2021/9/7 14:36
 * 开始发消息  测试确认
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //发消息
    @GetMapping("sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData("1");
        //故意改错  在交换机名称后加123 模拟交换机收不到消息的场景
//        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME+"123",ConfirmConfig.CONFIRM_ROUTING_KEY,message,correlationData);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY,message,correlationData);
        log.info("发送消息内容为:{}" +"key1",message);


        CorrelationData correlationData1 = new CorrelationData("2");
        //故意改错  在ROUTING_KEY名称后加2 模拟队列收不到消息的场景
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,ConfirmConfig.CONFIRM_ROUTING_KEY+"2",message,correlationData1);
        log.info("发送消息内容为:{}" +"key12",message);
    }
}
