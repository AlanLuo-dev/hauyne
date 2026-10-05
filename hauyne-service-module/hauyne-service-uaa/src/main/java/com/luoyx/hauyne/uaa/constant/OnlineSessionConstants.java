package com.luoyx.hauyne.uaa.constant;


public final class OnlineSessionConstants {

    private OnlineSessionConstants() {
    }

    /**
     * OAuth2Authorization.attributes 中保存在线会话 ID。
     */
    public static final String SESSION_ID_ATTRIBUTE =
            "online_session_id";

    /**
     * Access Token 中保存在线会话 ID。
     */
    public static final String SESSION_ID_CLAIM =
            "sid";

    /**
     * 在线
     */
    public static final int STATUS_ONLINE = 1;

    /**
     * 离线
     */
    public static final int STATUS_OFFLINE = 0;

    /**
     * 注销原因：主动注销
     */
    public static final String LOGOUT_REASON_LOGOUT = "LOGOUT";

    /**
     * 注销原因：管理员强制下线
     */
    public static final String LOGOUT_REASON_KICKOUT = "KICKOUT";

    /**
     * 注销原因：Token失效
     */
    public static final String LOGOUT_REASON_TOKEN_EXPIRED = "TOKEN_EXPIRED";
}
