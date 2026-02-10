package com.luoyx.hauyne.admin.api.sys.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否已启用 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum EnabledEnum implements EnumDefinition<Boolean, EnabledEnum> {

    ENABLED(true, "已启用"),
    DISABLED(false, "已禁用");

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
