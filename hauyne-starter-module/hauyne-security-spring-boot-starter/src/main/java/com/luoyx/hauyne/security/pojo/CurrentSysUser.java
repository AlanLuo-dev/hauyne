package com.luoyx.hauyne.security.pojo;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 当前登录的系统用户
 * <p>
 * 只提供get方法
 *
 * @author luoyingxiong
 */
@Getter
public class CurrentSysUser extends User implements Serializable {

    private static final long serialVersionUID = 5504902189915665407L;

    /**
     * 主键，无符号自增
     */
    private final Long id;

    /**
     * 昵称
     */
    private final String nickname;

    /**
     * 用户全名
     */
    private final String realName;

    /**
     * 头像地址
     */
    private final String avatar;

    /**
     * 手机号
     */
    private final String phone;

    /**
     * 电子邮箱
     */
    private final String email;

    /**
     * 职位
     */
    private final String position;

    /**
     * 所在部门
     */
    private final String department;

    private final List<Menu> menus;

    public CurrentSysUser(String username,
                          String password,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities,
                          Long id,
                          String nickname,
                          String realName,
                          String avatar,
                          String phone,
                          String email,
                          String position,
                          String department,
                          List<Menu> menus) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.id = id;
        this.nickname = nickname;
        this.realName = realName;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.department = department;
        this.menus = menus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CurrentSysUser user) {
            return this.getUsername().equals(user.getUsername());
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }
}