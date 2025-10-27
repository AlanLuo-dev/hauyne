package com.luoyx.hauyne.audit;

import com.luoyx.hauyne.admin.api.sys.dto.TriggerUserSnapshotSyncDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        rabbitTemplate.convertAndSend("trigger.user.snapshot.sync.exchange", "trigger.user.snapshot.sync.routing", triggerUserSnapshotSyncDTO);
    }
}
