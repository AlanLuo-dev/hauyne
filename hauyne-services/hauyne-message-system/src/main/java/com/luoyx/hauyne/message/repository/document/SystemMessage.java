package com.luoyx.hauyne.message.repository.document;

import com.luoyx.hauyne.message.support.MessageType;
import com.luoyx.hauyne.message.support.WebMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author 罗英雄
 * @date 2021/9/9 17:56
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Document(indexName = "message", type = "SystemMessage", shards = 1, replicas = 0, refreshInterval = "-1")
public class SystemMessage {

    @Id
    private String id;

    private Date dataTime;

    private String fromName;

    private Long recipientId;

    private Boolean isPrivate;

    private MessageType messageType;

    private Object payload;

    private Map<String, Object> headers;

    public SystemMessage(WebMessage<?> message, Long recipientId, Boolean isPrivate) {
        this.id = UUID.randomUUID().toString();
        this.dataTime = message.getDataTime();
        this.fromName = message.getFromName();
        this.recipientId = recipientId;
        this.isPrivate = isPrivate;
        this.messageType = message.getMessageType();
        this.payload = message.getPayload();
        this.headers = message.getHeaders();
    }
}
