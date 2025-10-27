package com.luoyx.hauyne.audit.mq.producer;

import com.luoyx.hauyne.audit.base.ExchangeName;
import com.luoyx.hauyne.audit.base.RoutingKeyName;
import com.luoyx.hauyne.audit.vo.NotifyMsgSendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Slf4j
@Component
public class NotifyMsgProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(NotifyMsgSendVO notifyMsgSendVO){
        log.debug("生产消息【{}】", notifyMsgSendVO);
        this.rabbitTemplate.convertAndSend(ExchangeName.NOTIFY_MSG.name(), RoutingKeyName.NOTIFY_MSG.name(), notifyMsgSendVO);
    }
}
