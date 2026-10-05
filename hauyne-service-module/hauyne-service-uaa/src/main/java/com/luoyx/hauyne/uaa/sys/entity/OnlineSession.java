package com.luoyx.hauyne.uaa.sys.entity;


import com.luoyx.hauyne.mybatisplus.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnlineSession extends IdEntity<OnlineSession> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 在线会话ID
     */
    private String sessionId;

    /**
     * OAuth2Authorization ID
     */
    private String authorizationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户登录名
     */
    private String principalName;

    /**
     * OAuth2客户端ID
     */
    private String clientId;

    /**
     * 授权模式
     */
    private String grantType;

    /**
     * 登录IP
     */
    private String ipAddress;

    /**
     * IP归属地
     */
    private String location;

    /**
     * User-Agent
     */
    private String userAgent;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 浏览器版本
     */
    private String browserVersion;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 最后活跃时间
     */
    private LocalDateTime lastActiveTime;

    /**
     * Access Token过期时间
     */
    private LocalDateTime accessTokenExpiresAt;

    /**
     * Refresh Token过期时间
     */
    private LocalDateTime refreshTokenExpiresAt;

    /**
     * 状态：1在线，0离线
     */
    private Integer status;

    /**
     * 注销时间
     */
    private LocalDateTime logoutTime;

    /**
     * 注销原因
     */
    private String logoutReason;
}
