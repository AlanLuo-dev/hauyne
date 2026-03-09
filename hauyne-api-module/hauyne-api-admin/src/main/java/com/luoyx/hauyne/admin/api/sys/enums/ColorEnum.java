package com.luoyx.hauyne.admin.api.sys.enums;

import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumSpec<String, ColorEnum> {
    RED("red", "红色"),
    BLUE("blue", "蓝色");

    private final String value;
    private final String label;
}
