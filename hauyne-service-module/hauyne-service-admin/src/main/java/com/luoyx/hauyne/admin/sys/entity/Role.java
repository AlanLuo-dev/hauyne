package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javers.core.metamodel.annotation.TypeName;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@TableName("hyn_sys_role")
@SuppressWarnings("serial")
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
}
