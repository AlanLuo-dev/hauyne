package com.luoyx.hauyne.audit.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 表示某个字段的一次值变更
 */
@Data
@Builder
public class FieldChangeVO {

    /**
     * 字段名
     */
    @Schema(description = "字段名")
    private String property;

    @Schema(description = "字段中文名（展示给用户查看）")
    private String label;

    /**
     * 原始值（left）
     */
    @Schema(description = "旧值")
    private Object oldValue;

    /**
     * 新值（right）
     */
    @Schema(description = "新值")
    private Object newValue;

}
