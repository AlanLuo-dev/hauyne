package com.luoyx.hauyne.admin.sys.query;


import com.luoyx.hauyne.common.enums.EnableStatusEnum;
import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典选项 查询参数
 */
@Getter
@Setter
@ToString
public class DictItemQuery {

    /**
     * 字典类型Id
     */
    @Schema(description = "字典类型Id")
    @NotNull(message = "字典类型Id不能为空")
    private Long dictTypeId;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictItemCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictItemName;

    /**
     * 启用状态【true=启用，false=禁用】
     */
    @Schema(description = "启用状态【true=启用，false=禁用】")
    @EnumCheck(message = "启用状态的枚举值不合法", enumClazz = EnableStatusEnum.class, getterMethod = "getBoolValue")
    private Boolean enabled;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortField;

    /**
     * 排序方式（1=升序，-1=降序）
     */
    @Schema(description = "排序方式（1=升序，-1=降序）", example = "-1")
    @EnumCheck(message = "排序方式枚举值不合法", enumClazz = PageQuery.SortOrderEnum.class, getterMethod = "getValue")
    private String sortOrder;
}
