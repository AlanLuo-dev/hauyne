package com.luoyx.hauyne.audit.mq.consumer;

import com.luoyx.hauyne.audit.base.QueueNames;
import com.luoyx.hauyne.audit.vo.NotifyMsgSendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotifyMsgConsumer {

    @RabbitListener(queues = QueueNames.NOTIFY_MSG_QUEUE)
    public void msgSend(NotifyMsgSendVO vo) {
        System.out.println("消费者收到消息：" + vo);
    }
}
