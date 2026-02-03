package com.luoyx.hauyne.web.enums.convert;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 枚举转换错误组：用于存储枚举转换过程中出现的错误信息
 */
@Data
public class EnumConvertErrorGroup {

    /**
     * 枚举名称
     */
    private String enumName;               // 颜色

    /**
     * 无效值集合
     */
    private Set<String> invalidValues;     // [pink, purple]

    public EnumConvertErrorGroup(String enumName) {
        this.enumName = enumName;
        this.invalidValues = new LinkedHashSet<>();
    }

    public void addInvalidValue(String value) {
        this.invalidValues.add(value);
    }
}

