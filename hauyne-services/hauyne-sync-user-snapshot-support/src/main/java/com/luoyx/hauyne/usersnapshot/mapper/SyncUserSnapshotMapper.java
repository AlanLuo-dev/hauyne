package com.luoyx.hauyne.usersnapshot.mapper;

import com.luoyx.hauyne.usersnapshot.entity.SyncUserSnapshot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SyncUserSnapshotMapper {

    /**
     * 新增用户快照
     *
     * @param syncUserSnapshot 用户快照实体
     * @return 影响的行数
     */
    int insert(@Param("syncUserSnapshot") SyncUserSnapshot syncUserSnapshot, @Param("tableName") String tableName);

    /**
     * 更新用户快照
     *
     * @param syncUserSnapshot 用户快照实体
     * @return 影响的行数
     */
    int update(@Param("syncUserSnapshot") SyncUserSnapshot syncUserSnapshot, @Param("tableName") String tableName);

    /**
     * 根据用户ID列表查询用户快照
     *
     * @param userIds 用户ID列表
     * @return 用户快照实体列表
     */
    List<SyncUserSnapshot> selectByIds(@Param("userIds") List<Long> userIds, @Param("tableName") String tableName);
}
