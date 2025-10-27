package com.luoyx.hauyne.admin.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {


    @Bean
    public DirectExchange triggerUserSnapshotSyncDirectExchange() {
        return new DirectExchange("trigger.user.snapshot.sync.exchange");
    }

    @Bean
    public Queue triggerUserSnapshotSyncQueue() {
        return new Queue("trigger.user.snapshot.sync.queue", true);
    }

    @Bean
    public Binding triggerUserSnapshotSyncBinding() {
        return BindingBuilder
                .bind(triggerUserSnapshotSyncQueue())
                 .to(triggerUserSnapshotSyncDirectExchange())
                .with("trigger.user.snapshot.sync.routing");
    }

    /*~ START 登录历史 MQ配置 ***************************/

    @Bean
    public DirectExchange loginHistoryDirectExchange() {
        return new DirectExchange("LOGIN_HISTORY_EXCHANGE");
    }

    @Bean
    public Queue loginHistoryQueue() {
        return new Queue("LOGIN_HISTORY_QUEUE", true);
    }

    @Bean
    public Binding loginHistoryQueueBinding() {
        return BindingBuilder
                .bind(loginHistoryQueue())
                .to(loginHistoryDirectExchange())
                .with("LOGIN_HISTORY_ROUTING");
    }

    /*~ END   登录历史 MQ配置 ***************************/
}
