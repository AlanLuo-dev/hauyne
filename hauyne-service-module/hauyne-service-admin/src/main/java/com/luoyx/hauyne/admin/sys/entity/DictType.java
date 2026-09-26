package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.admin.api.sys.enums.IconTypeEnum;
import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据字典类型表
 * </p>
 *
 * @author LuoYingxiong
 * @since 2022-05-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hyn_sys_dict_type")
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
     * 是否启用（1=启用；0=禁用; 无符号）
     */
    @TableField(value = "is_enabled")
    private Boolean enabled;

    /**
     * 是否为系统内置角色（0=否，1=是）
     */
    @TableField(value = "is_builtin")
    private YesNoEnum builtin;

    /**
     * 图标类型（iconfont=iconfont图标，material-symbols=Material Symbols图标，ng-zorro=NG-ZORRO UI库自带图标）
     */
    private IconTypeEnum iconType;

    /**
     * 描述
     */
    private String description;
}
