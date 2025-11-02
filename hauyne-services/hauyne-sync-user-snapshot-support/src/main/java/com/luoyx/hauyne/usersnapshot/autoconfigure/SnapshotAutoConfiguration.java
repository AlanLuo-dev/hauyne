package com.luoyx.hauyne.usersnapshot.autoconfigure;

import com.luoyx.hauyne.usersnapshot.amqp.QueueConfig;
import com.luoyx.hauyne.usersnapshot.amqp.UserSnapshotAmqpConsumer;
import com.luoyx.hauyne.usersnapshot.converter.UserSnapshotConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 用户快照模块的自动配置类
 * 该类负责自动配置用户快照模块的相关组件，包括 AMQP 消费者、转换器和队列配置。
 *
 * @author Alan.Luo
 */
@Configuration
@Import({UserSnapshotAmqpConsumer.class, UserSnapshotConverter.class, QueueConfig.class})
public class SnapshotAutoConfiguration {
}

