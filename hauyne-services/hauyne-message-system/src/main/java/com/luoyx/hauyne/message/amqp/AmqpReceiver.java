package com.luoyx.hauyne.message.amqp;

import com.luoyx.hauyne.message.service.MessageService;
import com.luoyx.hauyne.message.support.Message;
import com.luoyx.hauyne.message.support.WebMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 罗英雄
 * @date 2021/9/9 11:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AmqpReceiver {

    private static final String ACCOUNT = "account";

    private static final String OA = "oa";

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
//    private final MemberClient memberClient;

    /**
     * 发送消息给所有用户前端
     *
     * @param message
     */
    @RabbitListener(queues = "center.message.all.${spring.rabbitmq.name}", priority = "${spring.rabbitmq.priority:0}")
    public void receiverFanoutMessageToAll(@Payload Message<?> message) {
        try {
            messagingTemplate.convertAndSend("/topic/message", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    /**
     * 发送消息给指定用户前端
     *
     * @param message
     */
    @RabbitListener(queues = "center.message.user.${spring.rabbitmq.name}", priority = "${spring.rabbitmq.priority:0}")
    public void receiverFanoutMessageToUser(@Payload Message<?> message) {
        try {
            messagingTemplate.convertAndSendToUser(message.getHeaderValue(ACCOUNT), "/queue/message", message);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    /**
     * 发送消息给指定的订阅路径
     */
    @RabbitListener(queues = "center.websocket.all.${spring.rabbitmq.name}", priority = "${spring.rabbitmq.priority:0}")
    public void receiverWebsocketToAll(@Payload Map<String, String> message) {
        try {
            String account = message.get("account");

            // 指定用户发送
            if (account != null) {
                Arrays.stream(account.split(",")).forEach(item -> messagingTemplate.convertAndSendToUser(item, message.get("destination"), message.get("payload")));
            } else {
                messagingTemplate.convertAndSend(message.get("destination"), message.get("payload"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    /**
     * 发送消息给所有用户
     */
    @RabbitListener(queues = "center.message.all", priority = "${spring.rabbitmq.priority:0}")
    public void receiverMessageToAll(@Payload Message<?> message) {
        try {
            messageService.sendMessageToAll((WebMessage<?>) message);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    /**
     * 发送消息给指定用户
     */
    @RabbitListener(queues = "center.message.user", priority = "${spring.rabbitmq.priority:0}")
    public void receiverMessageToUser(@Payload Message<?> message) {
        try {
            String oa = message.getHeaderValue(OA);
            String account = message.getHeaderValue(ACCOUNT);
            if (StringUtils.isNotBlank(oa) || StringUtils.isNotBlank(account)) {
                List<String> sendAccounts;
                if (StringUtils.isNotBlank(account)) {
                    sendAccounts = Arrays.stream(oa.split(",")).map(item -> {
                        Object member = null;
                        if (null != member) {
                            return member.toString(); // member.getAccount;
                        } else {
                            return null;
                        }
                    }).collect(Collectors.toList());
                } else {
                    sendAccounts = Arrays.asList(account.split(","));
                }
                try {
                    WebMessage<?> webMessage = (WebMessage<?>) message;
                    messageService.sendMessageToUsers(new HashSet<>(sendAccounts), webMessage.getFromName(), webMessage.getPayload(), webMessage.getMessageType()
                            , webMessage.getHeaderValue("urlParam"));
                } catch (Exception e) {
                    log.error(e.getMessage(), e.getCause());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
