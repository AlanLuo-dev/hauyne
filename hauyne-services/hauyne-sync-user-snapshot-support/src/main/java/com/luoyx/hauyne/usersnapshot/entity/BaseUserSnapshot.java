package com.luoyx.hauyne.usersnapshot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户快照 公共字段
 *
 * @author Alan.Luo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseUserSnapshot<T extends BaseUserSnapshot<T>> extends Model<T> {

    /**
     * 主键（使用hyn_sys_user_profile 表的 id）
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
