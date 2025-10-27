package com.luoyx.hauyne.admin.amqp.producer;

import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.luoyx.hauyne.admin.api.sys.constant.MQConstant.USER_SNAPSHOT_FANOUT_EXCHANGE;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserSnapshotProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(List<UserSnapshotMessage> userSnapshotMessageList) {
        log.debug("生产消息【{}】", userSnapshotMessageList);
        this.rabbitTemplate.convertAndSend(USER_SNAPSHOT_FANOUT_EXCHANGE, "",userSnapshotMessageList);
    }
}
