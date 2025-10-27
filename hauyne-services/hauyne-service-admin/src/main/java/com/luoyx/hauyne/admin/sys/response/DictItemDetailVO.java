package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 数据字典选项详情VO类(表单回显)
 * </p>
 *
 * @author 1079032853@qq.com
 * @since 2021-04-15
 */
@Getter
@Setter
@ToString
public class DictItemDetailVO {

    /**
     * id
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典选项编码
     */
    @Schema(description = "字典选项编码")
    private String dictItemCode;

    /**
     * 字典选项名称
     */
    @Schema(description = "字典选项名称")
    private String dictItemName;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态【true=已启用；false=已禁用】")
    private Boolean enabled;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
