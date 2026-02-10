package com.luoyx.hauyne.usersnapshot.converter;

import com.luoyx.hauyne.usersnapshot.entity.SyncUserSnapshot;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;

import java.util.function.Supplier;

/**
 * 用户快照Bean转换器
 */
public class UserSnapshotConverter {

    /**
     * 将用户快照消息转换为实体
     *
     * @param msg    用户快照消息
     * @param entity 实体对象
     * @param <U>    实体类型
     */
    public void updateEntityFromMsg(UserSnapshotMessage.UserSnapshot msg, SyncUserSnapshot entity) {
        entity.setId(msg.getId());
        entity.setRealName(msg.getRealName());
        entity.setNickname(msg.getNickname());
        entity.setAvatar(msg.getAvatar());
        entity.setLastUpdatedTime(msg.getLastUpdatedTime());
    }

    /**
     * 将用户快照消息转换为实体
     *
     * @param msg      用户快照消息
     * @param supplier 实体对象的供应器
     * @param <U>      实体类型
     * @return 转换后的实体对象
     */
    public SyncUserSnapshot toEntity(UserSnapshotMessage.UserSnapshot msg, Supplier<SyncUserSnapshot> supplier) {
        SyncUserSnapshot u = supplier.get();
        updateEntityFromMsg(msg, u);
        return u;
    }

}
