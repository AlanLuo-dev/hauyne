package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统管理服务 用户信息-快照表
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hyn_sys_user_snapshot")
public class UserSnapshot extends Model<UserSnapshot> {

    /**
     * 主键（用户id）
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 最后修改时间（使用hyn_sys_user_profile 表的 last_updated_time）
     */
    @TableField(value = "last_updated_time")
    private LocalDateTime lastUpdatedTime;

    @Override
    public Serializable pkVal() {
        return id;
    }
}
