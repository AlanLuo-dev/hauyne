package com.luoyx.hauyne.usersnapshot.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户快照 公共字段
 *
 * @author Alan.Luo
 */
@Data
public class SyncUserSnapshot {

    /**
     * 主键（用户id）
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
     * 最后修改时间（使用hyn_sys_user_profile 表的 last_updated_time）
     */
    private LocalDateTime lastUpdatedTime;

}
