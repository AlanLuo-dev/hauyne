package com.luoyx.hauyne.usersnapshot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@Getter
@RequiredArgsConstructor
public enum EventType {

    /**
     * 创建用户
     */
    CREATE,

     /**
     * 更新用户
     */
    UPDATE,

     /**
     * 删除用户
     */
    DELETE,

    /**
     * 消费者服务启动完成
     */
    CONSUMER_SERVER_STARTED;
}
