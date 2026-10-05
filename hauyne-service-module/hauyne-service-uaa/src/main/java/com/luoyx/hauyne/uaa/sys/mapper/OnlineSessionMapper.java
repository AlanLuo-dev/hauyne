package com.luoyx.hauyne.uaa.sys.mapper;


import com.luoyx.hauyne.uaa.sys.entity.OnlineSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OnlineSessionMapper {

    int insert(OnlineSession session);

    OnlineSession selectBySessionId(
            @Param("sessionId") String sessionId
    );

    OnlineSession selectByAuthorizationId(
            @Param("authorizationId") String authorizationId
    );

    List<OnlineSession> selectOnlineSessions();

    List<OnlineSession> selectOnlineSessionsByUserId(
            @Param("userId") Long userId
    );

    int updateLastActiveTime(
            @Param("sessionId") String sessionId,
            @Param("lastActiveTime") LocalDateTime lastActiveTime
    );

    int markOffline(
            @Param("authorizationId") String authorizationId,
            @Param("logoutReason") String logoutReason,
            @Param("logoutTime") LocalDateTime logoutTime
    );

    int markOfflineBySessionId(
            @Param("sessionId") String sessionId,
            @Param("logoutReason") String logoutReason,
            @Param("logoutTime") LocalDateTime logoutTime
    );

    int markOfflineByUserId(
            @Param("userId") Long userId,
            @Param("logoutReason") String logoutReason,
            @Param("logoutTime") LocalDateTime logoutTime
    );

    int updateTokenExpiresAt(
            @Param("authorizationId") String authorizationId,
            @Param("accessTokenExpiresAt") LocalDateTime accessTokenExpiresAt,
            @Param("refreshTokenExpiresAt") LocalDateTime refreshTokenExpiresAt
    );
}
