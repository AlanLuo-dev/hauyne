package com.luoyx.hauyne.admin.api.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否枚举（false=否，true=是）
 */
@Getter
@RequiredArgsConstructor
public enum YesNoEnum implements EnumSpec<Boolean, YesNoEnum> {

    NO(false, "否"),
    YES(true, "是");

    @EnumValue
    private final Boolean value;
    private final String label;
}
