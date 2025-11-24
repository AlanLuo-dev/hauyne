package com.luoyx.hauyne.usersnapshot.service.impl;

import com.luoyx.hauyne.framework.utils.JsonUtil;
import com.luoyx.hauyne.usersnapshot.converter.UserSnapshotConverter;
import com.luoyx.hauyne.usersnapshot.entity.SyncUserSnapshot;
import com.luoyx.hauyne.usersnapshot.enums.EventType;
import com.luoyx.hauyne.usersnapshot.mapper.SyncUserSnapshotMapper;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import com.luoyx.hauyne.usersnapshot.service.SyncUserSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息-快照表 服务实现类
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "hauyne.user-snapshot.table-name")
public class SyncUserSnapshotServiceImpl implements SyncUserSnapshotService {

    /**
     * 各个微服务的数据库的用户快照表的表名
     */
    @Value("${hauyne.user-snapshot.table-name}")
    private String tableName;

    private final SyncUserSnapshotMapper syncUserSnapshotMapper;
    private final UserSnapshotConverter converter;


    /**
     * 同步用户快照
     *
     * @param userSnapshotMessage 用户快照消息
     */
    @Override
    public void syncUserSnapshot(UserSnapshotMessage userSnapshotMessage) {
        if (userSnapshotMessage == null) {
            log.info("用户快照MQ消息为空，不进行同步");
            return;
        }
        final List<UserSnapshotMessage.UserSnapshot> userSnapshotList = userSnapshotMessage.getUserSnapshotList();
        if (CollectionUtils.isEmpty(userSnapshotList)) {
            log.info("用户快照MQ消息中的用户快照列表为空，不进行同步");
            return;
        }

        List<Long> userIds = userSnapshotList.stream().map(UserSnapshotMessage.UserSnapshot::getId).toList();
        Map<Long, SyncUserSnapshot> snapshotMap = syncUserSnapshotMapper.selectByIds(userIds, tableName).stream()
                .collect(Collectors.toMap(SyncUserSnapshot::getId, t -> t));

        for (UserSnapshotMessage.UserSnapshot msg : userSnapshotList) {
            final Long msgUserId = msg.getId();
            if (snapshotMap.containsKey(msgUserId)) {
                SyncUserSnapshot snapshot = snapshotMap.get(msgUserId);
                final LocalDateTime msgLastUpdatedTime = msg.getLastUpdatedTime();
                final LocalDateTime snapshotLastUpdatedTime = snapshot.getLastUpdatedTime();

                // 如果是删除事件，或者消息中的更新时间大于快照中的更新时间，才需要更新
                if (EventType.DELETE.equals(userSnapshotMessage.getEventType())
                        || msgLastUpdatedTime.isAfter(snapshotLastUpdatedTime)) {
                    converter.updateEntityFromMsg(msg, snapshot);
                    syncUserSnapshotMapper.update(snapshot, tableName);
                    if (EventType.DELETE.equals(userSnapshotMessage.getEventType())) {
                        log.info("删除用户,事件为【{}】，更新用户快照 for 用户id={} ", userSnapshotMessage.getEventType(), JsonUtil.toString(userSnapshotMessage.getUserSnapshotList()));
                    } else {
                        log.info("更新用户快照 for 用户id={} 消息中的更新时间={} 数据库中快照的更新时间={}", msgUserId, msgLastUpdatedTime, snapshotLastUpdatedTime);
                    }
                }
            } else {
                SyncUserSnapshot snapshot = converter.toEntity(msg, SyncUserSnapshot::new);
                syncUserSnapshotMapper.insert(snapshot, tableName);
            }
        }
    }
}
