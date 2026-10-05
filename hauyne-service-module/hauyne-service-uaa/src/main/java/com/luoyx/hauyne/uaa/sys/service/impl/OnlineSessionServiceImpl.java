package com.luoyx.hauyne.uaa.sys.service.impl;


import com.luoyx.hauyne.uaa.constant.OnlineSessionConstants;
import com.luoyx.hauyne.uaa.sys.entity.OnlineSession;
import com.luoyx.hauyne.uaa.sys.mapper.OnlineSessionMapper;
import com.luoyx.hauyne.uaa.sys.service.OnlineSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OnlineSessionServiceImpl implements OnlineSessionService {

    private final OnlineSessionMapper onlineSessionMapper;


    @Override
    @Transactional
    public OnlineSession create(
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
    ) {

        LocalDateTime now = LocalDateTime.now();

        OnlineSession session = new OnlineSession();

        session.setSessionId(sessionId);
        session.setAuthorizationId(authorization.getId());

        session.setUserId(userId);
        session.setPrincipalName(principalName);

        session.setClientId(clientId);
        session.setGrantType(grantType);

        session.setIpAddress(ipAddress);
        session.setLocation(location);

        session.setUserAgent(userAgent);
        session.setBrowser(browser);
        session.setBrowserVersion(browserVersion);
        session.setOsName(osName);

        session.setLoginTime(now);
        session.setLastActiveTime(now);

        if (authorization.getAccessToken() != null) {
            session.setAccessTokenExpiresAt(
                    toLocalDateTime(
                            authorization.getAccessToken()
                                    .getToken()
                                    .getExpiresAt()
                    )
            );
        }

        if (authorization.getRefreshToken() != null) {
            session.setRefreshTokenExpiresAt(
                    toLocalDateTime(
                            authorization.getRefreshToken()
                                    .getToken()
                                    .getExpiresAt()
                    )
            );
        }

        session.setStatus(
                OnlineSessionConstants.STATUS_ONLINE
        );

        onlineSessionMapper.insert(session);

        return session;
    }

    private LocalDateTime toLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }

        return LocalDateTime.ofInstant(
                instant,
                ZoneId.systemDefault()
        );
    }


    @Override
    public OnlineSession findBySessionId(String sessionId) {

        return onlineSessionMapper.selectBySessionId(sessionId);
    }


    @Override
    public OnlineSession findByAuthorizationId(
            String authorizationId
    ) {

        return onlineSessionMapper.selectByAuthorizationId(
                authorizationId
        );
    }


    @Override
    public List<OnlineSession> findOnlineSessions() {

        return onlineSessionMapper.selectOnlineSessions();
    }


    @Override
    public List<OnlineSession> findOnlineSessions(Long userId) {

        return onlineSessionMapper.selectOnlineSessionsByUserId(
                userId
        );
    }


    @Override
    public void touch(
            String sessionId,
            LocalDateTime lastActiveTime
    ) {

        onlineSessionMapper.updateLastActiveTime(
                sessionId,
                lastActiveTime
        );
    }


    @Override
    @Transactional
    public void kickout(
            String authorizationId,
            String reason
    ) {

        onlineSessionMapper.markOffline(
                authorizationId,
                reason,
                LocalDateTime.now()
        );
    }


    @Override
    @Transactional
    public void kickoutBySessionId(
            String sessionId,
            String reason
    ) {

        onlineSessionMapper.markOfflineBySessionId(
                sessionId,
                reason,
                LocalDateTime.now()
        );
    }


    @Override
    @Transactional
    public void kickoutUser(
            Long userId,
            String reason
    ) {

        onlineSessionMapper.markOfflineByUserId(
                userId,
                reason,
                LocalDateTime.now()
        );
    }


    @Override
    public void updateTokenExpiresAt(
            String authorizationId,
            LocalDateTime accessTokenExpiresAt,
            LocalDateTime refreshTokenExpiresAt
    ) {

        onlineSessionMapper.updateTokenExpiresAt(
                authorizationId,
                accessTokenExpiresAt,
                refreshTokenExpiresAt
        );
    }


    private String generateSessionId() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }
}