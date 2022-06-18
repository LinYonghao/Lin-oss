package com.linyonghao.oss.manager.config;

import com.linyonghao.oss.manager.Constant.MQName;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
public class MQConfig {

    @Bean
    Queue smsQueue(){
        return new Queue(MQName.SMS_QUEUE,true,false,false);
    }
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(MQName.TOPIC_EXCHANGE_NAME,true,false);
    }

    @Bean
    Binding bindSMS(){
        return BindingBuilder.bind(topicExchange())
                .to(topicExchange())
                .with(MQName.SMS_QUEUE);
    }


}
