package com.luoyx.hauyne.admin.sys.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 帐户是否未锁定 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum AccountNonLockedEnum implements EnumDefinition<Boolean, AccountNonLockedEnum> {

    NORMAL(true, "正常"),
    EXPIRED(false, "已锁定");

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
