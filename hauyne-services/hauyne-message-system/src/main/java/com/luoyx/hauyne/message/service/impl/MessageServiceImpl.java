//package com.luoyx.hauyne.message.service.impl;
//
//import com.luoyx.hauyne.message.repository.SystemMessageRepository;
//import com.luoyx.hauyne.message.repository.document.SystemMessage;
//import com.luoyx.hauyne.message.service.MessageService;
//import com.luoyx.hauyne.message.support.MessageType;
//import com.luoyx.hauyne.message.support.WebMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.IterableUtils;
//import org.apache.commons.lang.time.DateUtils;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @author 罗英雄
// * @date 2021/9/9 22:43
// */
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MessageServiceImpl implements MessageService {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final SystemMessageRepository systemMessageRepository;
////    private final Memberclient memberclient;
//
//    /**
//     * 发送消息给所有用户
//     * @param message 消息
//     * @param <T>
//     */
//    @Override
//    public <T> void sendMessageToAll(WebMessage<T> message) {
//        if (message.getMessageType().equals(MessageType.SYSTEM_NOTIFY)
//                || message.getMessageType().equals(MessageType.SYSTEM_MESSAGE)
//                || !ObjectUtils.isEmpty(message.getPayload())) {
//            systemMessageRepository.save(new SystemMessage(message, null, false));
//        }
//        rabbitTemplate.convertAndSend("center.message.all", "", message);
//    }
//
//    @Override
//    public <T> void sendMessageToAll(T message, MessageType messageType) {
//        sendMessageToAll("系统通知", message, messageType);
//    }
//
//    @Override
//    public <T> void sendMessageToAll(String fromName, T message, MessageType messageType) {
//        WebMessage<Object> webMessage = new WebMessage<>(message, messageType);
//        webMessage.setFromName(fromName);
//        sendMessageToAll(webMessage);
//    }
//
//    /**
//     * 发送消息给特定用户
//     * @param account
//     * @param message     消息
//     * @param messageType
//     * @param <T>
//     */
//    @Override
//    public <T> void sendMessageToUser(String account, T message, MessageType messageType) {
//        sendMessageToUser(account, null, message, messageType, null);
//    }
//
//    @Override
//    public <T> void sendMessageToUser(String account, T message, MessageType messageType, Object urlParam) {
//        sendMessageToUser(account, null, message, messageType, urlParam);
//    }
//
//    @Override
//    public <T> void sendMessageToUser(String account, String fromName, T message, MessageType messageType, Object urlParam) {
//        WebMessage<Object> webMessage = new WebMessage<>(message, messageType);
//        if (fromName == null) {
//            webMessage.setFromName("系统通知");
//        } else {
//            webMessage.setFromName(fromName);
//        }
//
//        log.info("发送消息给: " + account);
//
//        try {
//            if (messageType.equals(MessageType.SYSTEM_NOTIFY)
//                    || messageType.equals(MessageType.SYSTEM_MESSAGE)
//                    || !ObjectUtils.isEmpty(message)) {
//                Object member = null;// memberclient.findbyAccount(account);
//                if (member != null) {
//                    systemMessageRepository.save(new SystemMessage(webMessage, Long.valueOf( "0" /* member.getid */),true));
//                }
//            }
//        } catch (Exception e) {
//            log.error("保存消息时报错: {}", e.getMessage());
//        }
//
//
//        // 广播消息给前端
//        webMessage.setHeaderValue("account", account);
//        if (!ObjectUtils.isEmpty(urlParam)) {
//            webMessage.setHeaderValue("urlParam", urlParam);
//        }
//        rabbitTemplate.convertAndSend("center.message.user", "", webMessage);
//    }
//
//    /**
//     * 发送消息给指定用户组
//     * @param accounts
//     * @param message 消息
//     * @param messageType
//     * @param <T>
//     */
//    @Override
//    public <T> void sendMessageToUsers(Set<String> accounts, T message, MessageType messageType) {
//        accounts.forEach(account -> sendMessageToUser(account, message, messageType));
//    }
//
//    /**
//     * 发送消息给指定用户组
//     * @param accounts
//     * @param fromName
//     * @param message 消息
//     * @param messageType
//     * @param <T>
//     */
//    @Override
//    public <T> void sendMessageToUsers(Set<String> accounts, String fromName, T message, MessageType messageType) {
//        accounts.forEach(account -> sendMessageToUser(account, fromName, message, messageType, null));
//    }
//
//    @Override
//    public <T> void sendMessageToUsers(Set<String> accounts, String fromName, T message, MessageType messageType, Object urlParam) {
//        accounts.forEach(account -> sendMessageToUser(account, fromName, message, messageType, urlParam));
//    }
//
//    /**
//     * 从redis中获取用户消息
//     * @param searchText 搜索字符串
//     * @param id         用户账户
//     * @return 消息队列
//     */
//    @Override
//    public List<WebMessage> getUserMessage(String searchText, Long id) {
//        BoolQueryBuilder qb = QueryBuilders.boolQuery();
//        qb.must(QueryBuilders.rangeQuery("dataTime").gte(DateUtils.truncate(new Date(), Calendar.DATE).getTime()));
//        qb = qb.must(QueryBuilders.boolQuery()
//                .should(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("isPrivate", true)).must(QueryBuilders.termQuery("recipientId", id)))
//                .should(QueryBuilders.termQuery("isPrivate", false))
//        );
//        if (!ObjectUtils.isEmpty(searchText)) {
//            qb = qb.must(QueryBuilders.boolQuery()
//                    .should(QueryBuilders.matchPhraseQuery("payload", searchText))
//                    .should(QueryBuilders.matchPhraseQuery("fromName", searchText))
//            );
//        }
//        List<SystemMessage> messageList = IterableUtils.toList(systemMessageRepository.search(qb, PageRequest.of(0, 9999, Sort.Direction.ASC, "dataTime")));
//        log.info("返回用户消息---{}", id);
//
//        return messageList.stream().map(
//                item -> {
//                    WebMessage message = new WebMessage<>(item.getFromName(), item.getPayload(), item.getMessageType());
//                    message.setDataTime(item.getDataTime());
//                    message.setHeaders(item.getHeaders());
//
//                    return message;
//                }
//        ).collect(Collectors.toList());
//    }
//
//    /**
//     * 从redis删除未读消息
//     * @param account 用户账户
//     */
//    @Override
//    public void deleteUnreadMessage(String account) {
//        /*String unReadKey = CHAT_UNREAD + account;
//        if (redisTemplate.hashKey(unReadKey)) {
//            redisTemplate.delete(unReadKey);
//        }*/
//    }
//
//    /**
//     * 查询未读消息数量
//     * @param account 用户账户
//     * @return 未读
//     */
//    @Override
//    public Long unreadMessageCount(String account) {
////        String unReadKey = CHAT_UNREAD + account;
////        return operations.size(unReadKey);
//
//        return null;
//    }
//}
