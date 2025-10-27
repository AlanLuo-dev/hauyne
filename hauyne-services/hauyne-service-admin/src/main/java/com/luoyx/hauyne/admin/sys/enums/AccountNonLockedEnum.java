package com.luoyx.hauyne.admin.sys.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 帐户是否未锁定 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum AccountNonLockedEnum {

    NORMAL(true, "正常"),
    EXPIRED(false, "已锁定");

    /**
     * 原始值
     */
    private final Boolean value;

    /**
     * 文本
     */
    private final String text;

    /**
     * 帐户是否未锁定 键值对
     * <p>
     * key: 原始值
     * value: 文本
     */
    public static final Map<Boolean, String> MAP;

    static {
        Map<Boolean, String> statusMap = new HashMap<>();
        for (AccountNonLockedEnum item : AccountNonLockedEnum.values()) {
            statusMap.put(item.getValue(), item.getText());
        }
        MAP = Collections.unmodifiableMap(statusMap);
    }
}
