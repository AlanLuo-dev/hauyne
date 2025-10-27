package com.luoyx.hauyne.usersnapshot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luoyx.hauyne.usersnapshot.entity.BaseUserSnapshot;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;

import java.util.List;

/**
 * <p>
 * 用户信息-快照表 服务接口
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
public interface UserSnapshotService<E extends BaseUserSnapshot<E>> extends IService<E> {

    /**
     * 同步用户快照
     *
     * @param userSnapshotMessageList 用户快照MQ消息列表
     */
    void syncUserSnapshot(List<UserSnapshotMessage> userSnapshotMessageList);
}
