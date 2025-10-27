package com.luoyx.hauyne.audit.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.luoyx.hauyne.audit.api.enums.AuditTypeEnum;
import lombok.Data;

/**
 * Javers审计日志 MQ消息类
 *
 * @param <T> 消息体类型
 * @author Alan.Luo
 */
@Data
public class JaversAuditMessage<T> {

    /**
     * 审计日志类型 (commit=提交, commitShallowDelete=删除)
     */
    private AuditTypeEnum auditType;

    /**
     * 作者的用户Id
     */
    private Long authorId;

    /**
     * 消息体（即业务数据）
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    private T payload;
}
