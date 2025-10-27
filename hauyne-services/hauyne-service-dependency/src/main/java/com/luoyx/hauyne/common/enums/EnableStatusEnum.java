package com.luoyx.hauyne.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 启用状态 枚举类
 *
 * @author luoyingxiong
 */
@Getter
@AllArgsConstructor
public enum EnableStatusEnum {

    /**
     * 启用
     */
    ENABLE(1, true, "启用"),

    /**
     * 禁用
     */
    DISABLE(0, false, "禁用");

    private final Integer intValue;
    private final Boolean boolValue;
    private final String text;

    public static boolean validateIntValue(Integer intValue) {
        if (null == intValue) {
            return false;
        }
        for (EnableStatusEnum enableStatusEnum : EnableStatusEnum.values()) {
            if (enableStatusEnum.intValue.equals(intValue)) {
                return true;
            }
        }

        return false;
    }

    public static boolean validateBoolValue(Boolean boolValue) {
        if (null == boolValue) {
            return false;
        }
        for (EnableStatusEnum enableStatusEnum : EnableStatusEnum.values()) {
            if (enableStatusEnum.boolValue.equals(boolValue)) {
                return true;
            }
        }

        return false;
    }

    public static String getTextByValue(Boolean boolValue) {
        if (null == boolValue) {
            return null;
        }
        for (EnableStatusEnum enableStatusEnum : EnableStatusEnum.values()) {
            if (enableStatusEnum.boolValue.equals(boolValue)) {
                return enableStatusEnum.getText();
            }
        }

        return null;
    }
}