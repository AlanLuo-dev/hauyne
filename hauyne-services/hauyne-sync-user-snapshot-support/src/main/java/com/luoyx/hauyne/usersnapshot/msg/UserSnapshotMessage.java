package com.luoyx.hauyne.usersnapshot.msg;

import com.luoyx.hauyne.usersnapshot.enums.EventType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户快照MQ消息
 *
 * @author Alan.Luo
 * @since 2025-09-20
 */
@Data
public class UserSnapshotMessage {

    private EventType eventType;

    private List<UserSnapshot> userSnapshotList;

    @Data
    public static class UserSnapshot {
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
}
