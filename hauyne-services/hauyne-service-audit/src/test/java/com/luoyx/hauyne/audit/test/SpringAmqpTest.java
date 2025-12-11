package com.luoyx.hauyne.audit.test;

import com.luoyx.hauyne.admin.api.sys.audit.RoleAuditDTO;
import com.luoyx.hauyne.audit.api.dto.JaversAuditMessage;
import com.luoyx.hauyne.audit.api.enums.AuditTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend() throws Exception {
        // 1.创建cd
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());

        cd.getFuture()
                .thenAccept(accept -> {
                    log.info("收到 confirm callback 回执");
                    if (accept.isAck()) {
                        log.info("消息发送成功，收到ack");
                    } else {
                        log.info("消息发送失败，收到nack，原因: {}", accept.getReason());
                    }
                })
                .exceptionally(ex -> {
                    log.error("消息回调失败", ex);
                    return null;
                });

        rabbitTemplate.convertAndSend("test.exchange", "test.routing2", "jack!", cd);

        Thread.sleep(5000);
        System.out.println("发送成功");
    }

    @Test
    public void testSendAuditMsg() {
        RoleAuditDTO roleAuditDTO = new RoleAuditDTO();
        roleAuditDTO.setRoleName("杰克");
        roleAuditDTO.setRoleCode("jack");
        roleAuditDTO.setId(2L);

        JaversAuditMessage<RoleAuditDTO> auditMessage = new JaversAuditMessage<>();
        auditMessage.setAuthorId(1L);
        auditMessage.setAuditType(AuditTypeEnum.COMMIT);
        auditMessage.setPayload(roleAuditDTO);

        rabbitTemplate.convertAndSend("test.exchange", "test.routing", auditMessage);
    }

    /**
     * 测试RabbitMQ的PageOut场景
     *
     */
    @Test
    public void testPageOut() {
        // 1.创建一条临时消息
        Message message = MessageBuilder
                .withBody("hello".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                .build();

        // 2.发送一百万条 临时消息
        for (int i = 0; i < 1000000; i++) {
            rabbitTemplate.send("test.exchange", "test.routing", message);
        }

        // 发送太多临时消息，MQ会出现阻塞，因为临时消息会被Page Out记录到磁盘中，这只是临时换页，不会持久化，重启MQ后会丢失
    }


}
