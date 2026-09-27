package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 数据字典选项表
 * </p>
 *
 * @author LuoYingxiong
 * @since 2022-06-04
 */
@Getter
@Setter
@ToString
@TableName("hyn_sys_dict_item")
@SuppressWarnings("serial")
public class DictItem extends BaseEntity<DictItem> {

    /**
     * 字典类型id
     */
    private Long dictTypeId;

    /**
     * 字典值
     */
    private String dictItemCode;

    /**
     * 字典名称
     */
    private String dictItemName;

    /**
     * 启用状态（1=启用；0=禁用 无符号）
     */
    @TableField(value = "is_enabled")
    private Boolean enabled;

    /**
     * 是否系统内置（0=否，1=是）
     */
    @TableField(value = "is_builtin")
    private YesNoEnum builtin;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序（无符号）
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
}
