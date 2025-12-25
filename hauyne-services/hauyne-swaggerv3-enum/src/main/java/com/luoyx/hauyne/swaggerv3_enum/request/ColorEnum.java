package com.luoyx.hauyne.swaggerv3_enum.request;

import com.luoyx.hauyne.web.enums.core.EnumSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorEnum implements EnumSchema<String, ColorEnum> {
    RED("red", "红色"),
    BLUE("blue", "蓝色");

    private final String value;
    private final String label;
}
