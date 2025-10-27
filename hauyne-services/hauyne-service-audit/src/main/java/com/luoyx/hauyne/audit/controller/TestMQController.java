package com.luoyx.hauyne.audit.controller;

import com.luoyx.hauyne.audit.mq.producer.NotifyMsgProducer;
import com.luoyx.hauyne.audit.vo.NotifyMsgSendVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.UUID;

@RestController
public class TestMQController {

    @Resource
    private NotifyMsgProducer notifyMsgProducer;

    @GetMapping("/produce")
    public String produce() {
        NotifyMsgSendVO notifyMsgSendVO = new NotifyMsgSendVO();
        notifyMsgSendVO.setPriKey(UUID.randomUUID().toString());
        notifyMsgSendVO.setPhoneNum("191xxxxyyyy");
        notifyMsgSendVO.setBusinessType("msg_send");
        notifyMsgProducer.send(notifyMsgSendVO);

        return "success";
    }

    @GetMapping("/testRPC")
    public String testRPC() {
        return "这是tracelog返回的数据";
    }
}
