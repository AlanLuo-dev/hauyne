package com.luoyx.hauyne.admin.sys.enums;

import com.luoyx.hauyne.web.enums.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumDef<String, ColorEnum> {
    RED("red", "红色"),
    BLUE("blue", "蓝色");

    private final String value;
    private final String label;
}
