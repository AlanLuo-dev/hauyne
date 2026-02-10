package com.luoyx.hauyne.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 罗英雄
 * @date 2021/9/9 11:31
 */
@Configuration
public class QueueConfig {

    @Value("${spring.rabbitmq.name}")
    private String name;

    @Bean
    public Queue queueFanoutMassageToAll() {
        return new Queue("center.message.all." + name, false);
    }

    @Bean
    public Queue queueFanoutMassageToUser() {
        return new Queue("center.message.user." + name, false);
    }

    @Bean
    FanoutExchange messageAllExchange() {
        return new FanoutExchange("center.message.all");
    }

    @Bean
    FanoutExchange messageUserExchange() {
        return new FanoutExchange("center.message.user");
    }

    @Bean
    public Binding bindingQueueMassageToAll() {
        return BindingBuilder.bind(queueFanoutMassageToAll()).to(messageAllExchange());
    }

    @Bean
    public Binding bindingQueueMassageToUser() {
        return BindingBuilder.bind(queueFanoutMassageToUser()).to(messageUserExchange());
    }

    @Bean
    public Queue queueMassageToAll() {
        return new Queue("center.message.all", true);
    }

    @Bean
    public Queue queueMassageToUser() {
        return new Queue("center.message.user", true);
    }

    @Bean
    public Queue queueFanoutWebsocketToAll() {
        return new Queue("center.websocket.all." + name, false);
    }

    @Bean
    FanoutExchange websocketToAllExchange() {
        return new FanoutExchange("center.websocket.all");
    }

    @Bean
    public Binding bindingQueueWebsocketToAll() {
        return BindingBuilder.bind(queueFanoutWebsocketToAll()).to(websocketToAllExchange());
    }



}
