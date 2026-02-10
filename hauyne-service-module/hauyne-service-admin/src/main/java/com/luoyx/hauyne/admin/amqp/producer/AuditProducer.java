package com.luoyx.hauyne.admin.amqp.producer;

import com.luoyx.hauyne.audit.api.constant.MQConstant;
import com.luoyx.hauyne.audit.api.dto.JaversAuditMessage;
import com.luoyx.hauyne.audit.api.enums.AuditTypeEnum;
import com.luoyx.hauyne.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuditProducer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 审计日志 以MQ消息形式发送到审计队列
     *
     * @param auditDTO 审计DTO
     */
    public <U> void sendToAudit(U auditDTO) {
        JaversAuditMessage<U> auditMessage = new JaversAuditMessage<>();
        auditMessage.setAuthorId(SecurityUtils.getCurrentSysUserId());
        auditMessage.setAuditType(AuditTypeEnum.COMMIT);
        auditMessage.setPayload(auditDTO);

        this.rabbitTemplate.convertAndSend(MQConstant.AUDIT_EXCHANGE, MQConstant.AUDIT_ROUTING, auditMessage);
    }



    /**
     * 审计日志 以MQ消息形式发送到审计队列（删除操作）
     *
     * @param auditDTO 审计DTO
     */
    public <U> void sendToShadowDelete(U auditDTO) {
        JaversAuditMessage<U> auditMessage = new JaversAuditMessage<>();
        auditMessage.setAuthorId(SecurityUtils.getCurrentSysUserId());
        auditMessage.setAuditType(AuditTypeEnum.SHALLOW_DELETE);
        auditMessage.setPayload(auditDTO);
        this.rabbitTemplate.convertAndSend(MQConstant.AUDIT_EXCHANGE, MQConstant.AUDIT_ROUTING, auditMessage);
    }
}
