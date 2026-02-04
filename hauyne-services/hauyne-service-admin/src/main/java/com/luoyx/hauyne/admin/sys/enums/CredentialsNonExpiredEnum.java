package com.luoyx.hauyne.admin.sys.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.web.enums.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 密码是否未过期 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum CredentialsNonExpiredEnum implements EnumDef<Boolean, CredentialsNonExpiredEnum> {

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
