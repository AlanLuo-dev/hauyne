package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典选项 Dropdown下拉列表框展现形式的数据
 *
 * @author 1564469545@qq.com
 */
@Getter
@Setter
@ToString
public class DictItemDropdownVO {

    /**
     * 字典选项编码
     */
    @Schema(description = "字典选项编码")
    private String value;

    /**
     * 字典选项名称
     */
    @Schema(description = "字典选项名称")
    private String label;
}
