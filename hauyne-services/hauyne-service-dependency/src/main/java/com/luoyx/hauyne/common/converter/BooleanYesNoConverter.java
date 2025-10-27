package com.luoyx.hauyne.common.converter;

/**
 * 布尔值转换为字符串是否
 *
 * @author luoyingxiong
 */
public class BooleanYesNoConverter {

    public static String asString(Boolean bool) {
        return null == bool ? null : (bool ? "是" : "否"
        );
    }

    public static Boolean asBoolean(String bool) {
        return null == bool ?
                null : (bool.trim().startsWith("是") ?
                Boolean.TRUE : Boolean.FALSE
        );
    }
}
