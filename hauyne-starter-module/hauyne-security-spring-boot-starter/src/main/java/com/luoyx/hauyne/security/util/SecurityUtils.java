package com.luoyx.hauyne.security.util;

import com.luoyx.hauyne.security.pojo.CurrentSysUser;
import com.luoyx.hauyne.security.pojo.Menu;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 获取当前用户信息 工具类
 *
 * @author 罗英雄
 */
public class SecurityUtils {

    private SecurityUtils() {

    }

    /**
     * 获取当前用户
     *
     * @return 当前用户信息
     */
    public static CurrentSysUser getCurrentSysUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        OAuth2IntrospectionAuthenticatedPrincipal principal = (OAuth2IntrospectionAuthenticatedPrincipal) authentication.getPrincipal();
        Map<String, Object> tokenAttributes = principal.getAttributes();
        Map<String, Object> principalMap = (Map<String, Object>) tokenAttributes.get("principal");

        Collection<? extends GrantedAuthority> authorities = getAuthorities(principalMap);

        /**
         * 对于 Map<String, Object>由于我们使用Object类型来接收数字，map并不知道我们传入的是Long还是Integer。所以Map会根据我们传入的数值的大小来判断。
         *
         * 也就是说如果你传入的数值的大小是在 Integer.MIN_VALUE、 Integer.MAX_VALUE 之间，Map 就认为是 Integer 类型，如果超过了这个范围就认为是 Long 类型。
         */
        final Long id;
        Object value = principalMap.get("id");
        if (value instanceof Integer) {
            id = ((Integer) value).longValue();
        } else {
            id = (Long) value;
        }
        boolean enabled = principalMap.get("enabled") != null && (boolean) principalMap.get("enabled");
        boolean accountNonExpired = principalMap.get("accountNonExpired") != null && (boolean) principalMap.get("accountNonExpired");
        boolean credentialsNonExpired = principalMap.get("credentialsNonExpired") != null && (boolean) principalMap.get("credentialsNonExpired");
        boolean accountNonLocked = principalMap.get("accountNonLocked") != null && (boolean) principalMap.get("accountNonLocked");
        String nickname = (String) principalMap.get("nickname");
        String realName = (String) principalMap.get("realName");
        String avatar = (String) principalMap.get("avatar");
        String phone = (String) principalMap.get("phone");
        String email = (String) principalMap.get("email");
        String position = (String) principalMap.get("position");
        String department = (String) principalMap.get("department");
        List<Menu> menus = (List<Menu>) principalMap.get("menus");


        CurrentSysUser currentSysUser = new CurrentSysUser(
                (String) principalMap.get("username"),
                "N/A",
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                new HashSet<>(authorities),
                id,
                nickname,
                realName,
                avatar,
                phone,
                email,
                position,
                department,
                menus
        );

        return currentSysUser;

    }

    /**
     * 获取当前用户id
     *
     * @return 当前用户id
     */
    public static Long getCurrentSysUserId() {
        CurrentSysUser currentSysUser = getCurrentSysUser();
        if (null != currentSysUser) {
            return currentSysUser.getId();
        }

        return null;
    }

    /**
     * 获取当前用户的权限信息
     *
     * @param map 从token中解析出的principalMap
     * @return 当前用户的权限信息
     */
    private static Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get("authorities");
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}

