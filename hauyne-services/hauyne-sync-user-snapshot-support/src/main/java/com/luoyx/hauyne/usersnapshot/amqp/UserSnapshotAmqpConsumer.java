package com.luoyx.hauyne.usersnapshot.amqp;

import com.luoyx.hauyne.framework.utils.JsonUtil;
import com.luoyx.hauyne.usersnapshot.entity.BaseUserSnapshot;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import com.luoyx.hauyne.usersnapshot.service.UserSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RabbitMQ 消费者
 *
 * @author Alan.Luo
 */
@Slf4j
@RequiredArgsConstructor
public class UserSnapshotAmqpConsumer<T extends BaseUserSnapshot<T>> {

    private final UserSnapshotService<T> userSnapshotService;

    /**
     * 监听用户快照队列
     * <p>
     * queue: 微服务名 + .user.snapshot.queue
     * exchange: user.snapshot.fanout.exchange
     *
     * @param messages 用户快照MQ消息列表
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.application.name}.user.snapshot.queue"),
                    exchange = @Exchange(name = "user.snapshot.fanout.exchange", type = ExchangeTypes.FANOUT)
            )
    )
    public void consume(List<UserSnapshotMessage> messages) {
        log.info("消费者收到消息：{}", JsonUtil.toString(messages));
        userSnapshotService.syncUserSnapshot(messages);
    }

}
