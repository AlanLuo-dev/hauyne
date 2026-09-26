package com.luoyx.hauyne.admin.api.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.luoyx.hauyne.api.enumsupport.EnumSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 图标类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum IconTypeEnum implements EnumSpec<String, IconTypeEnum> {

    ICONFONT("iconfont", "iconfont图标"),
    MATERIAL_SYMBOLS("material-symbols", "Material Symbols图标"),
    NG_ZORRO("ng-zorro", "NG-ZORRO自带图标");

    @EnumValue
    private final String value;
    private final String label;
}
