package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 角色-权限资源中间表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-07-30
 */
@Data
@TableName("hyn_sys_role_authority")
public class RoleAuthority {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long authorityId;

    /**
     * 创建人id
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
