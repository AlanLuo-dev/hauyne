package com.luoyx.hauyne.admin.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 枚举类：RSA私钥存储在Redis中的Key配置
 *
 * @author 1564469545@qq.com
 * @date 2023/4/2 13:13
 */
@AllArgsConstructor
@Getter
public enum PrivateKeyRedisKeyEnum {

    MODIFY_PASSWORD("modifyPassword", "modifyPwd:", 60L, TimeUnit.SECONDS),
    RESET_PASSWORD("resetPassword", "resetPwd:", 60L, TimeUnit.SECONDS);

    /**
     * 业务
     */
    private final String business;

    /**
     * Redis Key的前缀（含业务意义，管理Redis的时候容易区分不同业务的数据）
     */
    private final String keyPrefix;

    /**
     * 过期时间
     */
    private final long timeout;

    /**
     * 时间单位
     */
    private final TimeUnit timeUnit;

    /* --------------------------------------------------------------------- */

    public static final Map<String, PrivateKeyRedisKeyEnum> map;
    public static final Set<String> businessSet;

    static {
        Map<String, PrivateKeyRedisKeyEnum> tempMap = new LinkedHashMap<>();
        for (PrivateKeyRedisKeyEnum item : PrivateKeyRedisKeyEnum.values()) {
            tempMap.put(item.getBusiness(), item);
        }
        map = Collections.unmodifiableMap(tempMap);
        businessSet = Collections.unmodifiableSet(map.keySet());
    }

    /**
     * 判断参数合法性
     */
    public static boolean validate(String business) {
        if (businessSet.contains(business)) {
            return true;
        }

        return false;
    }
}
