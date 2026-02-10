package com.luoyx.hauyne.jpa.query;

import com.luoyx.hauyne.jpa.enums.SortOrderEnum;
import com.luoyx.hauyne.validation.constraint.EnumCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;

/**
 * PrimeNg 分页查询条件
 *
 * @author Alan.Luo
 */
@Getter
@Setter
public class PrimeNgPageQuery {

    /**
     * 偏移量，默认为0
     */
    @Schema(description = "偏移量，默认为0", example = "0")
    @Min(value = 0, message = "偏移量不能为负数")
    private Integer first = 0;

    /**
     * 每页显示行数，默认为10
     */
    @Schema(description = "每页显示行数，默认为10", example = "10")
    @Min(value = 10, message = "每页显示行数不能为负数")
    private Integer rows = 10;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortField;

    /**
     * 排序方式（1=升序，-1=降序）
     */
    @Schema(description = "排序方式（1=升序，-1=降序）", example = "-1")
    @EnumCheck(message = "排序方式枚举值不合法", enumClazz = SortOrderEnum.class, getterMethod = "getFrontEndSortOrder")
    private Integer sortOrder;

    private Integer pageNo;

    public Integer getPageNo() {
        return first / rows;
    }
}
