package com.luoyx.hauyne.audit.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * RabbitMQ 消息转换器
     * 1. 发送消息时，将对象转换为json字符串
     * 2. 接收消息时，将json字符串转换为对象
     *
     * @return jackson消息转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}