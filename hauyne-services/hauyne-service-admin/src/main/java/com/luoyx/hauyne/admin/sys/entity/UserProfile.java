package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户档案表
 * </p>
 *
 * @author 罗英雄
 * @since 2022-06-22
 */
@Getter
@Setter
@ToString
@TableName("hyn_sys_user_profile")
public class UserProfile extends Model<UserProfile> implements Serializable {

    /**
     * 主键（使用hyn_sys_user表的 id）
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户全名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别(1=男 0=女)
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 职位
     */
    private String position;

    /**
     * 备注
     */
    private String remark;

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

    /**
     * 最后修改人
     */
    @TableField(value = "last_updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedBy;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastUpdatedTime;

    @Override
    public Serializable pkVal() {
        return id;
    }
}
