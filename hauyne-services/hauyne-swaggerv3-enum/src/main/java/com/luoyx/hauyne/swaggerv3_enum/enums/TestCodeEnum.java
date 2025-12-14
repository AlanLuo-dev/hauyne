package com.luoyx.hauyne.swaggerv3_enum.enums;

import com.luoyx.hauyne.web.enums.core.EnumSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestCodeEnum implements EnumSchema<Integer, TestCodeEnum> {

    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private final Integer value;
    private final String label;

}
