package com.luoyx.hauyne.message.support;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗英雄
 * @date 2021/9/9 21:45
 */
public abstract class BaseMessage<T> implements Message<T> {

    private MessageType messageType;
    private T payload;
    private Map<String, Object> headers;

    public BaseMessage() {
    }

    public BaseMessage(T payload, MessageType messageType) {
        this(payload, null, messageType);
    }

    public BaseMessage(T payload, Map<String, Object> headers, MessageType messageType) {
        this.payload = payload;
        this.headers = headers;
        this.messageType = messageType;
    }

    @Override
    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public void setHeaderValue(String headerName, Object value) {
        if (ObjectUtils.isEmpty(this.headers)) {
            this.headers = new HashMap<>(1);
        }
        this.headers.put(headerName, value);
    }


    @Override
    public <U> U getHeaderValue(String headerName) {
        if (!ObjectUtils.isEmpty(this.headers)) {
            return (U) this.headers.get(headerName);
        } else {
            return null;
        }
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
