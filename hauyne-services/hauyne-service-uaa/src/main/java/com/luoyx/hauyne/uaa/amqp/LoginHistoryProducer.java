package com.luoyx.hauyne.uaa.amqp;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHistoryProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(SaveLoginHistoryDTO loginHistoryDTO){
        log.debug("生产登录历史消息【{}】", loginHistoryDTO);
        this.rabbitTemplate.convertAndSend("LOGIN_HISTORY_EXCHANGE", "LOGIN_HISTORY_ROUTING", loginHistoryDTO);
    }
}
