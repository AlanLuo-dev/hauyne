package com.luoyx.hauyne.eventlog.mq.consumer;

import com.luoyx.hauyne.eventlog.base.QueueNames;
import com.luoyx.hauyne.eventlog.vo.NotifyMsgSendVO;
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
