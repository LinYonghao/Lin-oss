package com.linyonghao.linosscore.config;

import com.linyonghao.linosscore.constant.MQName;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    Queue uploadLogQueue(){
        return new Queue(MQName.UPLOAD_LOG,true,false,false);
    }

    @Bean
    Queue uploadCallbackQueue(){
        return new Queue(MQName.UPLOAD_CALLBACK,true,false,false);
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(MQName.TOPIC_EXCHANGE_NAME,true,false);
    }

    @Bean
    Binding bindUploadLog(){
        return BindingBuilder.bind(uploadLogQueue())
                .to(topicExchange())
                .with(MQName.UPLOAD_LOG);
    }

    @Bean
    Binding bindUploadCallback(){
        return BindingBuilder.bind(uploadLogQueue())
                .to(topicExchange())
                .with(MQName.UPLOAD_CALLBACK);
    }


}
