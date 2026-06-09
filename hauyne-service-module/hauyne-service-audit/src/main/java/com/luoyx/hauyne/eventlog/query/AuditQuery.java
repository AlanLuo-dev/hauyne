package com.luoyx.hauyne.eventlog.query;

import com.luoyx.hauyne.eventlog.enums.AuditEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 审计查询条件
 *
 * @author Alan.Luo
 */
@Data
public class AuditQuery {

    /**
     * 数据的主键ID
     */
    @Schema(description = "数据的主键ID")
    @NotNull(message = "数据的主键ID 必填！")
    private Long localId;

    /**
     * 数据的日志类型名称
     */
    @Schema(description = "数据的日志类型名称")
    @NotNull(message = "日志类型名称不合法")
    private AuditEnum auditEnum;
}
