package com.luoyx.hauyne.web.me;


import java.io.Serializable;
import java.util.Objects;

/**
 * 枚举统一接口：所有需要统一返回的枚举必须实现
 *
 * @param <K>
 * @param <T>
 */
public interface BaseEnum<K extends Serializable, T extends Enum<T> & BaseEnum<K, T>> {

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

    /**
     * 根据编码获取枚举实例
     */
//    static <T extends Enum<T> & BaseEnum<? extends Serializable, T>> T getByValue(Serializable value, Class<T> enumClass) {
//        if (value == null) {
//            return null;
//        }
//        for (T enumConstant : enumClass.getEnumConstants()) {
//            if (Objects.equals(enumConstant.getValue(), value)) {  // 枚举编码
//                return enumConstant;
//            }
//        }
//        throw new IllegalArgumentException("无效的枚举编码: " + value + ", 枚举类: " + enumClass.getName());
//    }
}

