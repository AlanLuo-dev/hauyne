package com.luoyx.hauyne.admin.amqp.consumer;

import com.luoyx.hauyne.admin.api.sys.dto.SaveLoginHistoryDTO;
import com.luoyx.hauyne.admin.sys.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHistoryConsumer {

    private final LoginHistoryService loginHistoryService;

    @RabbitListener(queues = "login.history.queue")
    public void handleLoginHistory(SaveLoginHistoryDTO saveLoginHistoryDTO) {
        log.info("接收到登录日志消息：" + saveLoginHistoryDTO);
        loginHistoryService.save(saveLoginHistoryDTO);
    }
}
