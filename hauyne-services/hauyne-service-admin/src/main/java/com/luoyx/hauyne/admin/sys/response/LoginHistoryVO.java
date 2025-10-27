package com.luoyx.hauyne.admin.sys.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 登录历史Vo类
 *
 * @author 罗英雄
 * @date 2021/8/29 21:08
 */
@Getter
@Setter
@ToString
public class LoginHistoryVO {

    /**
     * 主键，无符号自增
     */
    private Long id;

    /**
     * 类型（1=登录；0=注销）
     */
    private String type;

    /**
     * 登录/注销结果（1=登录成功，2=登录失败，3=注销成功，4=注销失败）
     */
    private String result;

    /**
     * 原因
     */
    private String failReason;

    /**
     * 用户id（0=用户输入的账号不存在）
     */
    private String username;

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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
