package com.luoyx.hauyne.admin.sys.response;

import lombok.Data;

/**
 * 数据字典选项 Dropdown下拉列表框展现形式的数据
 *
 * @author 1564469545@qq.com
 */
@Data
public class DictItemDropdownDTO {

    /**
     * 字典类型编码
     */
    private String dictTypeCode;

    /**
     * 字典选项编码
     */
    private String value;

    /**
     * 字典选项名称
     */
    private String label;

    /**
     * 图标类型
     */
    private String iconType;

    /**
     * 图标
     */
    private String icon;
}
