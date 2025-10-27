package com.luoyx.hauyne.usersnapshot.msg;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户快照MQ消息
 *
 * @author Alan.Luo
 * @since 2025-09-20
 */
@Data
public class UserSnapshotMessage {

    /**
     * 用户id（即主键）
     */
    private Long id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastUpdatedTime;
}
