package com.luoyx.hauyne.usersnapshot.autoconfigure;

import com.luoyx.hauyne.usersnapshot.amqp.UserSnapshotAmqpConsumer;
import com.luoyx.hauyne.usersnapshot.converter.UserSnapshotConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserSnapshotAmqpConsumer.class, UserSnapshotConverter.class})
public class SnapshotAutoConfiguration {
}

