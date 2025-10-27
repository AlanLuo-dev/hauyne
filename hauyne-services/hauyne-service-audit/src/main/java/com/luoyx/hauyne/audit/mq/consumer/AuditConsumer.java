package com.luoyx.hauyne.audit.mq.consumer;

import com.luoyx.hauyne.audit.api.constant.MQConstant;
import com.luoyx.hauyne.audit.api.dto.JaversAuditMessage;
import com.luoyx.hauyne.audit.api.enums.AuditTypeEnum;
import com.luoyx.hauyne.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditConsumer {

    private final AuditService auditService;

    @RabbitListener(queues = MQConstant.AUDIT_QUEUE)
    public <U> void receive(JaversAuditMessage<U> auditMessage) {
        log.info("AuditConsumer 接收到消息: {}", auditMessage);

        Long authorId = auditMessage.getAuthorId();
        U payload = auditMessage.getPayload();
        AuditTypeEnum auditType = auditMessage.getAuditType();
        switch (auditType) {
            case COMMIT:
                auditService.audit(authorId, payload);
                break;
            case SHALLOW_DELETE:
                auditService.shadowDelete(authorId, payload);
                break;
        }
    }


}
