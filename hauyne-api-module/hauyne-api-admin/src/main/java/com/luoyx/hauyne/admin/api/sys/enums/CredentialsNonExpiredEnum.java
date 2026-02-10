package com.luoyx.hauyne.admin.api.sys.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 密码是否未过期 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum CredentialsNonExpiredEnum implements EnumDefinition<Boolean, CredentialsNonExpiredEnum> {

    NORMAL(true, "正常"),
    EXPIRED(false, "密码已过期");

    /**
     * 原始值
     */
    @EnumValue
    private final Boolean value;

    /**
     * 文本
     */
    private final String label;

}
