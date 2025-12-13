package com.luoyx.hauyne.swaggerv3_enum.me;


import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举统一接口：所有需要统一返回的枚举必须实现
 *
 * @param <K>
 * @param <T>
 */
public interface EnumSchema<K extends Serializable, T extends Enum<T> & EnumSchema<K, T>> {

    /**
     * 枚举编码
     *
     * @return 枚举编码
     */
    K getValue();

    /**
     * 枚举描述
     *
     * @return 枚举描述
     */
    String getLabel();

    Map<Class<?>, Map<? extends Serializable, String>> ENUM_MAP_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取当前枚举的「不可变键值对Map」（通用实现）
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    default Map<K, String> getMap() {
        Class<T> enumClass = (Class<T>) this.getClass();

        // 双重检查锁：避免并发重复生成
        if (!ENUM_MAP_CACHE.containsKey(enumClass)) {
            synchronized (EnumSchema.class) {
                if (!ENUM_MAP_CACHE.containsKey(enumClass)) {
                    Map<K, String> tempMap = new HashMap<>();
                    for (T enumConstant : enumClass.getEnumConstants()) {
                        tempMap.put(enumConstant.getValue(), enumConstant.getLabel());
                    }
                    ENUM_MAP_CACHE.put(enumClass, (Map<? extends Serializable, String>) Collections.unmodifiableMap(tempMap));
                }
            }
        }

        return (Map<K, String>) ENUM_MAP_CACHE.get(enumClass);
    }

    @SuppressWarnings("unchecked")
    static <K extends Serializable, T extends Enum<T> & EnumSchema<K, T>> Map<K, String> getMap(Class<T> enumClass) {
        final T[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null || enumConstants.length == 0) {
            return Collections.emptyMap();
        }

        // 触发枚举实例的getMap()生成缓存
        return enumConstants[0].getMap();
    }


    // 通用：根据枚举类型构建 Map<value, label>
    static <K extends Serializable, T extends Enum<T> & EnumSchema<K, T>> Map<K, String> map(Class<T> enumClass) {
        Map<K, String> statusMap = new HashMap<>();
        for (T item : enumClass.getEnumConstants()) {
            statusMap.put(item.getValue(), item.getLabel());
        }
        return Collections.unmodifiableMap(statusMap);
    }


}

