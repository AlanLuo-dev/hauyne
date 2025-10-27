package com.luoyx.hauyne.admin.sys.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 性别 枚举类
 *
 * @author LuoYingxiong
 */
@Getter
@RequiredArgsConstructor
public enum GenderEnum {

    MALE(1, "男"),
    FEMALE(0, "女");

    /**
     * 原始值
     */
    private final Integer value;

    /**
     * 文本
     */
    private final String label;

    /**
     * 性别 键值对
     * <p>
     * key: 原始值
     * value: 文本
     */
    public static final Map<Integer, String> MAP;

    static {
        Map<Integer, String> statusMap = new HashMap<>();
        for (GenderEnum item : GenderEnum.values()) {
            statusMap.put(item.getValue(), item.getLabel());
        }
        MAP = Collections.unmodifiableMap(statusMap);
    }
}
