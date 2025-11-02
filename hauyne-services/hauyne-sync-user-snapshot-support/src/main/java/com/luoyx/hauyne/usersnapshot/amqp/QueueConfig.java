package com.luoyx.hauyne.usersnapshot.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 队列配置类
 * 该类负责配置用户快照模块的 RabbitMQ 队列，包括触发用户快照同步的队列。
 *
 * @author Alan.Luo
 */
@Configuration
public class QueueConfig {

    /*~ START 触发用户快照同步的 RabbitMQ 交换机、队列、路由键及其绑定 配置 */
    @Bean
    public DirectExchange triggerUserSnapshotSyncDirectExchange() {
        return new DirectExchange("trigger.user.snapshot.sync.exchange");
    }

    @Bean
    public Queue triggerUserSnapshotSyncQueue() {
        return new Queue("trigger.user.snapshot.sync.queue");
    }

    @Bean
    public Binding triggerUserSnapshotSyncBinding() {
        return BindingBuilder
                .bind(triggerUserSnapshotSyncQueue())
                .to(triggerUserSnapshotSyncDirectExchange())
                .with("trigger.user.snapshot.sync.routing");
    }
    /*~ END 触发用户快照同步的 RabbitMQ 交换机、队列、路由键及其绑定 配置 */
}
