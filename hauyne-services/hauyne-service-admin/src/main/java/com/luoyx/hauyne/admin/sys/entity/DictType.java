package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 数据字典类型表
 * </p>
 *
 * @author LuoYingxiong
 * @since 2022-05-21
 */
@Getter
@Setter
@ToString
@TableName("hyn_sys_dict_type")
@SuppressWarnings("serial")
public class DictType extends BaseEntity<DictType> {

    /**
     * 字典类型编码
     */
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    private String dictTypeName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否启用（1=启用；0=禁用; 无符号）
     */
    @TableField(value = "is_enabled")
    private Boolean enabled;
}
