package com.luoyx.hauyne.admin.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.luoyx.hauyne.mybatisplus.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 登录历史表
 * </p>
 *
 * @author LuoYingxiong
 * @since 2021-04-17
 */
@Getter
@Setter
@ToString
@TableName("hyn_sys_login_history")
public class LoginHistory extends IdEntity<LoginHistory> {

    /**
     * 类型（1=登录；0=注销）
     */
    private Integer type;

    /**
     * 登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）
     */
    private Integer result;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 用户id（0=用户输入的账号不存在）
     */
    private Long userId;

    /**
     * 客户端ip
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 客户端浏览器
     */
    private String browser;

    /**
     * 客户端浏览器版本
     */
    private String browserVersion;

    /**
     * 客户端操作系统名称
     */
    private String osName;

    /**
     * 创建时间(自动填充)
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
