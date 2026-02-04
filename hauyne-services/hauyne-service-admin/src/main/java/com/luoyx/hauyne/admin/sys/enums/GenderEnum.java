package com.luoyx.hauyne.admin.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.web.enums.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 性别 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum GenderEnum implements EnumDef<Integer, GenderEnum> {

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
