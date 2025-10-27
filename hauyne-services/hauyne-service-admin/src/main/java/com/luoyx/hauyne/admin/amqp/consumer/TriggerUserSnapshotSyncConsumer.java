package com.luoyx.hauyne.admin.amqp.consumer;

import com.luoyx.hauyne.admin.api.sys.dto.TriggerUserSnapshotSyncDTO;
import com.luoyx.hauyne.admin.amqp.producer.UserSnapshotProducer;
import com.luoyx.hauyne.admin.sys.service.UserProfileService;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 触发用户快照同步消费者
 *
 * @author Alan.Luo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TriggerUserSnapshotSyncConsumer {

    private final UserSnapshotProducer userSnapshotProducer;
    private final UserProfileService userProfileService;

    /**
     * 其他服务启动完成后，会发送MQ消息到该队列，触发用户快照同步
     *
     * @param triggerUserSnapshotSyncDTO 触发用户快照同步DTO
     */
    @RabbitListener(queues = "trigger.user.snapshot.sync.queue")
    public void handleTriggerUserSnapshotSync(TriggerUserSnapshotSyncDTO triggerUserSnapshotSyncDTO) {
        log.info("收到服务【{}】的用户快照同步请求", triggerUserSnapshotSyncDTO.getServiceName());
        List<UserSnapshotMessage> userSnapshots = userProfileService.findUserSnapshots();
        userSnapshotProducer.send(userSnapshots);
    }
}
