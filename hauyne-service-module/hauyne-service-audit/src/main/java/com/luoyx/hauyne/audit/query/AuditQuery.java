package com.luoyx.hauyne.audit.query;

import com.luoyx.hauyne.audit.enums.AuditEnum;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
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
    private Long localId;

    /**
     * 数据的日志类型名称
     */
    @Schema(description = "数据的日志类型名称")
    @EnumCheck(enumClazz = AuditEnum.class, getterMethod = "getTypeName", message = "日志类型名称不正确")
    private String typeName;
}
