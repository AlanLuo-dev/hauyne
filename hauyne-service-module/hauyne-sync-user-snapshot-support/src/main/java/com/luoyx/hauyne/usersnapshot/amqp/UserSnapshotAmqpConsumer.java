package com.luoyx.hauyne.usersnapshot.amqp;

import com.luoyx.hauyne.framework.utils.JsonUtil;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import com.luoyx.hauyne.usersnapshot.service.SyncUserSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * RabbitMQ 消费者
 *
 * @author Alan.Luo
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "hauyne.user-snapshot.table-name")
public class UserSnapshotAmqpConsumer {

    private final SyncUserSnapshotService syncUserSnapshotService;

    /**
     * 用户快照消息消费者
     *
     * <p>监听用户快照广播消息，实现各微服务之间的用户快照数据同步。</p>
     *
     * <p><b>广播交换器：</b>user.snapshot.fanout.exchange<br>
     * 所有绑定到该交换器的队列都会收到同一份消息（不依赖 routing key）。</p>
     *
     * <p><b>队列：</b>${spring.application.name}.user.snapshot.queue<br>
     * 每个微服务实例拥有独立队列，实现解耦与广播订阅。</p>
     *
     * <p><b>消费逻辑：</b><br>
     * 接收用户快照消息后，执行本地用户快照数据同步。</p>
     *
     * @param message 用户快照消息
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(type = ExchangeTypes.FANOUT, name = "user.snapshot.fanout.exchange"),
                    value = @Queue(value = "${spring.application.name}.user.snapshot.queue")
            )
    )
    public void consume(UserSnapshotMessage message) {
        log.info("消费者收到消息：{}", JsonUtil.toString(message));
        syncUserSnapshotService.syncUserSnapshot(message);
    }

}
