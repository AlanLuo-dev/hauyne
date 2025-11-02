package com.luoyx.hauyne.audit;

import com.luoyx.hauyne.admin.api.sys.dto.TriggerUserSnapshotSyncDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TriggerUserSnapshotSyncRunner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始触发用户快照同步");
        TriggerUserSnapshotSyncDTO triggerUserSnapshotSyncDTO = new TriggerUserSnapshotSyncDTO();
        triggerUserSnapshotSyncDTO.setServiceName(serviceName);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().whenComplete((confirm, throwable) -> {
            if (throwable != null) {
                log.error("消息发送未知异常：消息ID为：" + correlationData.getId(), throwable);

            } else if (confirm.isAck()) {
                log.info("消息成功发送到交换机！消息ID为：{}", correlationData.getId());
            } else {
                log.error("消息发送到交换机失败！消息ID为：{}，失败原因：{}", correlationData.getId(), confirm.getReason());
            }
        });

        rabbitTemplate.convertAndSend(
                "trigger.user.snapshot.sync.exchange", "trigger.user.snapshot.sync.routing",
                triggerUserSnapshotSyncDTO, correlationData
        );
    }
}
