package com.luoyx.hauyne.usersnapshot.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyx.hauyne.usersnapshot.converter.UserSnapshotConverter;
import com.luoyx.hauyne.usersnapshot.entity.BaseUserSnapshot;
import com.luoyx.hauyne.usersnapshot.msg.UserSnapshotMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 抽象用户快照服务类
 *
 * @param <M> Mybatis Mapper 类型
 * @param <T> 用户快照实体类型
 */
@Slf4j
public abstract class AbstractUserSnapshotService<M extends BaseMapper<T>, T extends BaseUserSnapshot<T>>
        extends ServiceImpl<M, T> implements UserSnapshotService<T> {

    protected final UserSnapshotConverter converter;

    protected AbstractUserSnapshotService(UserSnapshotConverter converter) {
        this.converter = converter;
    }

    /**
     * 由子类返回实体构造器，比如 UserSnapshot::new
     *
     * @return 实体构造器
     */
    protected abstract Supplier<T> entitySupplier();

    /**
     * 同步用户快照
     *
     * @param userSnapshotMessageList 用户快照消息列表
     */
    @Override
    public void syncUserSnapshot(List<UserSnapshotMessage> userSnapshotMessageList) {
        if (CollectionUtils.isEmpty(userSnapshotMessageList)) {
            log.info("用户快照MQ消息列表为空，不进行同步");
            return;
        }

        List<Long> userIds = userSnapshotMessageList.stream().map(UserSnapshotMessage::getId).toList();
        Map<Long, T> snapshotMap = baseMapper.selectByIds(userIds).stream()
                .collect(Collectors.toMap(BaseUserSnapshot::getId, t -> t));

        for (UserSnapshotMessage msg : userSnapshotMessageList) {
            final Long userId = msg.getId();
            if (snapshotMap.containsKey(userId)) {
                T snapshot = snapshotMap.get(userId);
                final LocalDateTime msgLastUpdatedTime = msg.getLastUpdatedTime();
                final LocalDateTime snapshotLastUpdatedTime = snapshot.getLastUpdatedTime();
                if (msgLastUpdatedTime.isAfter(snapshotLastUpdatedTime)) {
                    converter.updateEntityFromMsg(msg, snapshot);
                    baseMapper.updateById(snapshot);
                } else {
                    log.info("丢弃过期的用户快照消息 for 用户id={} 消息中的更新时间={} 数据库中快照的更新时间={}", userId, msgLastUpdatedTime, snapshotLastUpdatedTime);
                }
            } else {
                T snapshot = converter.toEntity(msg, entitySupplier());
                baseMapper.insert(snapshot);
            }
        }
    }
}

