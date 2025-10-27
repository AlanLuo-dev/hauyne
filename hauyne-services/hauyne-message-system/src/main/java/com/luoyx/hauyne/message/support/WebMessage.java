package com.luoyx.hauyne.message.support;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Map;

/**
 * 发送消息给前端
 * @author 罗英雄
 * @date 2021/9/9 21:55
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebMessage<T> extends BaseMessage<T>{

    private Date dataTime;

    private String fromName;

    public WebMessage() {
        this.dataTime = new Date();
    }

    public WebMessage(T payload, MessageType messageType) {
        super(payload, messageType);
        this.dataTime = new Date();
    }

    public WebMessage(T payload, Map<String, Object> headers, MessageType messageType) {
        super(payload, headers, messageType);
        this.dataTime = new Date();
    }

    public WebMessage(String fromName, T payload, MessageType messageType) {
        super(payload, messageType);
        this.dataTime = new Date();
        this.fromName = fromName;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public WebMessage<T> urlParam(Object value) {
        this.setHeaderValue("urlParam", value);
        return this;
    }
}
