package com.luoyx.hauyne.audit.service;

import com.luoyx.hauyne.audit.query.AuditQuery;
import com.luoyx.hauyne.audit.response.AuditChangeVO;

import java.util.List;

/**
 * 审计日志 Service接口
 *
 * @author Alan.Luo
 */
public interface AuditService {

    /**
     * 审计（新增、修改）
     *
     * @param authorId 审计人 用户ID
     * @param auditDTO 审计DTO
     * @param <U>      审计DTO类型
     */
    <U> void audit(Long authorId, U auditDTO);

    /**
     * 审计（删除）
     *
     * @param authorId 审计人 用户ID
     * @param auditDTO 审计DTO
     * @param <U>      审计DTO类型
     */
    <U> void shadowDelete(Long authorId, U auditDTO);

    /**
     * 查询快照
     *
     * @param query 查询条件
     * @return 快照列表
     */
    String findSnapshots(AuditQuery query);

    /**
     * 查询影子
     *
     * @param query 查询条件
     * @return 影子列表
     */
    String findShadows(AuditQuery query);

    /**
     * 查询变更日志
     *
     * @param query 查询条件
     * @return 变更日志列表
     */
    String findChanges(AuditQuery query);

    /**
     * 查询分组后的变更日志
     *
     * @param query 查询条件
     * @return 变更日志列表
     */
    List<AuditChangeVO> groupChangesByCommit(AuditQuery query);
}
