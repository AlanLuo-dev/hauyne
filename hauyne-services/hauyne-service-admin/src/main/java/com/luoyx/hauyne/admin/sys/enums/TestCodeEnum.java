package com.luoyx.hauyne.admin.sys.enums;

import com.luoyx.hauyne.web.enums.core.EnumDef;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestCodeEnum implements EnumDef<Integer, TestCodeEnum> {

    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private final Integer value;
    private final String label;

}
