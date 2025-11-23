package com.luoyx.hauyne.audit.entity;

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
 * 审计日志微服务的 用户信息-快照表
 * </p>
 *
 * @author Alan.Luo
 * @since 2025-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hyn_audit_user_snapshot")
public class UserSnapshot extends Model<UserSnapshot> {

    /**
     * 主键（用户 id）
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
