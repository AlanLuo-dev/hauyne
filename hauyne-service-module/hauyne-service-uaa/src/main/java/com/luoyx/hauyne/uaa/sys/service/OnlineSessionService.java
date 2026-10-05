package com.luoyx.hauyne.uaa.sys.service;


import com.luoyx.hauyne.uaa.sys.entity.OnlineSession;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

import java.time.LocalDateTime;
import java.util.List;

public interface OnlineSessionService {

    /**
     * 创建在线会话
     */
    OnlineSession create(
            String sessionId,
            OAuth2Authorization authorization,
            Long userId,
            String principalName,
            String clientId,
            String grantType,
            String ipAddress,
            String location,
            String userAgent,
            String browser,
            String browserVersion,
            String osName
    );

    /**
     * 根据在线会话ID查询
     */
    OnlineSession findBySessionId(String sessionId);

    /**
     * 根据 OAuth2Authorization 查询
     */
    OnlineSession findByAuthorizationId(String authorizationId);

    /**
     * 查询所有在线用户
     */
    List<OnlineSession> findOnlineSessions();

    /**
     * 查询某个用户的所有在线会话
     */
    List<OnlineSession> findOnlineSessions(Long userId);

    /**
     * 更新最后活跃时间
     */
    void touch(String sessionId, LocalDateTime lastActiveTime);

    /**
     * 根据 OAuth2Authorization 强制下线
     */
    void kickout(
            String authorizationId,
            String reason
    );

    /**
     * 根据 sessionId 强制下线
     */
    void kickoutBySessionId(
            String sessionId,
            String reason
    );

    /**
     * 某用户全部下线
     */
    void kickoutUser(
            Long userId,
            String reason
    );

    /**
     * 更新 Token 过期时间
     */
    void updateTokenExpiresAt(
            String authorizationId,
            LocalDateTime accessTokenExpiresAt,
            LocalDateTime refreshTokenExpiresAt
    );
}
