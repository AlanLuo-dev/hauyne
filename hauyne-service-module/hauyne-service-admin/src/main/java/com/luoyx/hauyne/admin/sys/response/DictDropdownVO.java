package com.luoyx.hauyne.admin.sys.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 数据字典选项 Dropdown下拉列表框展现形式的数据
 *
 * @author 1564469545@qq.com
 */
@Data
public class DictDropdownVO {

    @Schema(description = "字典类型编码")
    private String dictTypeCode;

    @Schema(description = "字典列表")
    private List<OptionVO> options;

    @Data
    public static class OptionVO {
        @Schema(description = "字典编码")
        private String value;

        @Schema(description = "字典名称")
        private String label;

        @Schema(description = "图标类型")
        private String iconType;

        @Schema(description = "图标")
        private String icon;
    }
}
