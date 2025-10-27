package com.luoyx.hauyne.admin.sys.query;

import com.luoyx.hauyne.mybatisplus.query.PageQuery;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限查询条件Query类
 *
 * @author 罗英雄
 */
@Getter
@Setter
public class AuthorityQuery {

    /**
     * 权限类型
     */
    @Schema(description = "权限类型")
    private String authorityType;

    /**
     * 权限名称
     */
    @Schema(description = "权限名称")
    private String authorityName;

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
