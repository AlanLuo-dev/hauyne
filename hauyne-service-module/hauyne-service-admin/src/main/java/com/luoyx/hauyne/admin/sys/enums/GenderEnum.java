package com.luoyx.hauyne.admin.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 性别 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum GenderEnum implements EnumDefinition<Integer, GenderEnum> {

    MALE(1, "男"),
    FEMALE(0, "女");

    /**
     * 原始值
     */
    @EnumValue
    private final Integer value;

    /**
     * 文本
     */
    private final String label;


}
