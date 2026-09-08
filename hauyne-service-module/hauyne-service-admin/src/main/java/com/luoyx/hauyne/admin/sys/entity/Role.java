package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.admin.api.sys.enums.YesNoEnum;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javers.core.metamodel.annotation.TypeName;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hyn_sys_role")
@TypeName("hyn_sys_role")
public class Role extends BaseEntity<Role> {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否为系统内置角色（0=否，1=是）
     */
    @TableField(value = "is_builtin")
    private YesNoEnum builtin;

    /**
     * 权限菜单父子节点选中状态是否联动（0=否，1=是）
     */
    @TableField(value = "is_authority_check_linkage")
    private YesNoEnum authorityCheckLinkage;
}
