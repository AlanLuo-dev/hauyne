package com.luoyx.hauyne.admin.sys.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 帐户是否未过期 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum AccountNonExpiredEnum {

    NORMAL(true, "正常"),
    EXPIRED(false, "已过期");

    /**
     * 原始值
     */
    private final Boolean value;

    /**
     * 文本
     */
    private final String text;

    /**
     * 帐户是否未过期 键值对
     * <p>
     * key: 原始值
     * value: 文本
     */
    public static final Map<Boolean, String> MAP;

    static {
        Map<Boolean, String> statusMap = new HashMap<>();
        for (AccountNonExpiredEnum item : AccountNonExpiredEnum.values()) {
            statusMap.put(item.getValue(), item.getText());
        }
        MAP = Collections.unmodifiableMap(statusMap);
    }
}
