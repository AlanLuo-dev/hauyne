package com.luoyx.hauyne.admin.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    /*~ START 登录历史 MQ配置 ***************************/

    @Bean
    public DirectExchange loginHistoryDirectExchange() {
        return new DirectExchange("login.history.exchange");
    }

    @Bean
    public Queue loginHistoryQueue() {
        return new Queue("login.history.queue");
    }

    @Bean
    public Binding loginHistoryQueueBinding() {
        return BindingBuilder
                .bind(loginHistoryQueue())
                .to(loginHistoryDirectExchange())
                .with("login.history.routing");
    }

    /*~ END   登录历史 MQ配置 ***************************/
}
