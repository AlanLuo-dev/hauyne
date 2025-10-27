package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.IdEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 用户-角色中间表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-09-07
 */
@Builder
@Getter
@Setter
@ToString
@TableName("hyn_sys_user_role")
@SuppressWarnings("serial")
public class UserRole extends IdEntity<UserRole> {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建人
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
