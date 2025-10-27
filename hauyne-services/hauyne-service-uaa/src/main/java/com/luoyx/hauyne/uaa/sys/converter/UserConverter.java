package com.luoyx.hauyne.uaa.sys.converter;

import com.luoyx.hauyne.admin.api.sys.dto.UserDTO;
import com.luoyx.hauyne.security.pojo.CurrentSysUser;
import com.luoyx.hauyne.security.pojo.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author 罗英雄
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * 封装当前用户信息
     *
     * @param user  用户信息
     * @param menus 导航菜单
     * @return
     */
    @Mappings({

            // 用户基本信息
            @Mapping(target = "id", source = "user.userBaseInfo.id"),
            @Mapping(target = "username", source = "user.userBaseInfo.username"),
            @Mapping(target = "password", source = "user.userBaseInfo.password"),
            @Mapping(target = "accountNonExpired", source = "user.userBaseInfo.accountNonExpired"),
            @Mapping(target = "accountNonLocked", source = "user.userBaseInfo.accountNonLocked"),
            @Mapping(target = "credentialsNonExpired", source = "user.userBaseInfo.credentialsNonExpired"),
            @Mapping(target = "enabled", source = "user.userBaseInfo.enabled"),

            // 用户档案信息
            @Mapping(target = "nickname", source = "user.userProfile.nickname"),
            @Mapping(target = "realName", source = "user.userProfile.realName"),
            @Mapping(target = "avatar", source = "user.userProfile.avatar"),
            @Mapping(target = "phone", source = "user.userProfile.phone"),
            @Mapping(target = "email", source = "user.userProfile.email"),
            @Mapping(target = "position", source = "user.userProfile.position"),
            @Mapping(target = "department", constant = "test"),

            // 拥有的权限
            @Mapping(target = "authorities", source = "authorities"),

            @Mapping(target = "menus", source = "menus")
    })
    CurrentSysUser toCurrentSysUser(UserDTO user, List<SimpleGrantedAuthority> authorities, List<Menu> menus);
}
