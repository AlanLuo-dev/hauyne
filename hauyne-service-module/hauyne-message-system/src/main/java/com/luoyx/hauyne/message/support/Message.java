package com.luoyx.hauyne.message.support;

import java.util.Map;

/**
 * @author 罗英雄
 * @date 2021/9/9 21:43
 */
public interface Message<T> {

    T getPayload();

    Map<String, Object> getHeaders();

    <U> U getHeaderValue(String headerName);

    MessageType getMessageType();
}
