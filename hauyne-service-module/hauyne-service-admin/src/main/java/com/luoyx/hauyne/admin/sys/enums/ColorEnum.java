package com.luoyx.hauyne.admin.sys.enums;

import com.luoyx.hauyne.api.enums.core.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumDefinition<String, ColorEnum> {
    RED("red", "红色"),
    BLUE("blue", "蓝色");

    private final String value;
    private final String label;
}
