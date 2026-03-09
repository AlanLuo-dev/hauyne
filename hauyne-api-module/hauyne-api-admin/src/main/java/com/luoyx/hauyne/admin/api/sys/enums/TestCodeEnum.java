package com.luoyx.hauyne.admin.api.sys.enums;

import com.luoyx.hauyne.api.enumdef.EnumDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestCodeEnum implements EnumDefinition<Integer, TestCodeEnum> {

    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private final Integer value;
    private final String label;

}
