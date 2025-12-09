package com.luoyx.hauyne.admin.sys.enums;

import com.luoyx.hauyne.web.me.EnumSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestBoolEnum implements EnumSchema<Boolean, TestBoolEnum> {
    ZHENG(true, "是"),
    JIA(false, "否");

    private final Boolean value;
    private final String label;
}
