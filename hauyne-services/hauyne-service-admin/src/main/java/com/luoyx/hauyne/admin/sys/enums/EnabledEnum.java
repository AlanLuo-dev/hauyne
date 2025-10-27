package com.luoyx.hauyne.admin.sys.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 是否已启用 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum EnabledEnum {

    ENABLED(true, "已启用"),
    DISABLED(false, "已禁用");

    /**
     * 原始值
     */
    private final Boolean value;

    /**
     * 文本
     */
    private final String text;

    /**
     * 是否已启用 键值对
     * <p>
     * key: 原始值
     * value: 文本
     */
    public static final Map<Boolean, String> MAP;

    static {
        Map<Boolean, String> statusMap = new HashMap<>();
        for (EnabledEnum item : EnabledEnum.values()) {
            statusMap.put(item.getValue(), item.getText());
        }
        MAP = Collections.unmodifiableMap(statusMap);
    }
}
