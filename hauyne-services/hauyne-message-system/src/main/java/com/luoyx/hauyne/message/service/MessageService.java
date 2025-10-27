package com.luoyx.hauyne.message.service;

import com.luoyx.hauyne.message.support.MessageType;
import com.luoyx.hauyne.message.support.WebMessage;

import java.util.List;
import java.util.Set;

/**
 * @author 罗英雄
 * @date 2021/9/9 22:06
 */
public interface MessageService {

    /**
     * 发送消息给所有用户
     *
     * @param message 消息
     * @param <T>
     */
    <T> void sendMessageToAll(WebMessage<T> message);

    <T> void sendMessageToAll(T message, MessageType messageType);

    <T> void sendMessageToAll(String fromName, T message, MessageType messageType);


    /**
     * 发送消息给指定用户
     *
     * @param account
     * @param message     消息
     * @param messageType
     * @param <T>
     */
    <T> void sendMessageToUser(String account, T message, MessageType messageType);

    <T> void sendMessageToUser(String account, T message, MessageType messageType, Object urlParam);

    <T> void sendMessageToUser(String account, String fromName, T message, MessageType messageType, Object urlParam);

    /**
     * 发送消息给指定用户组
     *
     * @param accounts
     * @param message
     * @param messageType
     * @param <T>
     */
    <T> void sendMessageToUsers(Set<String> accounts, T message, MessageType messageType);

    <T> void sendMessageToUsers(Set<String> accounts, String fromName, T message, MessageType messageType);

    <T> void sendMessageToUsers(Set<String> accounts, String fromName, T message, MessageType messageType, Object urlParam);

    /**
     * 从redis中获取用户消息
     *
     * @param searchText 搜索字符串
     * @param id         用户账户
     * @return 消息队列
     */
    List<WebMessage> getUserMessage(String searchText, Long id);

    /**
     * 从redis中删除未读消息
     *
     * @param account 用户账户
     */
    void deleteUnreadMessage(String account);

    /**
     * 查询未读消息数量
     *
     * @param account 用户账户
     * @return 未读消息数量
     */
    Long unreadMessageCount(String account);
}
