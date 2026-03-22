package com.luoyx.hauyne.admin.amqp.consumer;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginHistoryConsumer {

    @RabbitListener(queues = "login.history.queue")
    public void handleLoginHistory(SaveLoginHistoryDTO saveLoginHistoryDTO) {
        log.info("接收到登录日志消息：" + saveLoginHistoryDTO);
    }
}
