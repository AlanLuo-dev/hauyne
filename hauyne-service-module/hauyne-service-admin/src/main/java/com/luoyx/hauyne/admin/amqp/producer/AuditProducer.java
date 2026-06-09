package com.luoyx.hauyne.admin.amqp.producer;

import com.luoyx.hauyne.eventlog.api.constant.MQConstant;
import com.luoyx.hauyne.eventlog.api.dto.JaversAuditMessage;
import com.luoyx.hauyne.eventlog.api.enums.AuditTypeEnum;
import com.luoyx.hauyne.framework.utils.JsonUtil;
import com.luoyx.hauyne.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditProducer {

    private final RabbitTemplate rabbitTemplate;
    private final AsyncTaskExecutor taskExecutor;

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
        log.info("事务状态: {}", TransactionSynchronizationManager.isSynchronizationActive());

        // 核心: 注册事务同步器
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        // 异步提交
                        taskExecutor.execute(() -> {
                            try {
                                log.info("监听到事务提交，开始发送操作日志: {}", JsonUtil.toString(auditMessage));
                                rabbitTemplate.convertAndSend(
                                    MQConstant.AUDIT_EXCHANGE, MQConstant.AUDIT_ROUTING, auditMessage
                                );
                            } catch (Exception e) {
                                // 可扩展: 失败日志、本地消息表重试
                                log.error("事务提交后异步发送MQ失败", e);
                            }
                        });
                    }
                }
        );
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
