package com.luoyx.hauyne.uaa.event;

/**
 * 登录成功事件
 *
 * @param userId    用户ID
 * @param username  用户名
 * @param ip        登录IP地址
 * @param loginType 登录类型
 *
 */
public record LoginSuccessEvent(
        Long userId,
        String username,
        String ip,
        String loginType
) {
}
