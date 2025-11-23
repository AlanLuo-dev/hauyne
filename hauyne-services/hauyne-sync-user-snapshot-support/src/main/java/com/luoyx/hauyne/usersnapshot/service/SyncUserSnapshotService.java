package com.luoyx.hauyne.usersnapshot.service;

import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;

/**
 * <p>
 * 用户信息-快照表 服务接口
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
public interface SyncUserSnapshotService {

    /**
     * 同步用户快照
     *
     * @param userSnapshotMessage 用户快照MQ消息
     */
    void syncUserSnapshot(UserSnapshotMessage userSnapshotMessage);


}
