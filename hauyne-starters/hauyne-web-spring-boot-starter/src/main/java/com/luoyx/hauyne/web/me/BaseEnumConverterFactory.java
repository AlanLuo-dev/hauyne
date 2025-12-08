package com.luoyx.hauyne.web.me;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * 枚举转换器工厂：将Serializable类型的枚举值转换为BaseEnum枚举对象
 */
public class BaseEnumConverterFactory<R extends Enum<R> & BaseEnum<? extends Serializable, R>>
        implements ConverterFactory<String, R> {

    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    public <T extends R> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return (Converter<String, T>) new BaseEnumConverter<>((Class<R>) targetType); // 内部转换器：实现具体的“值→枚举”转换
    }

    /**
     * 内部转换器：实现具体的“值→枚举”转换
     */
    private static class BaseEnumConverter<U extends Enum<U> & BaseEnum<? extends Serializable, U>>
            implements Converter<String, U> {

        // 目标枚举类型
        private final Class<U> enumType;

        public BaseEnumConverter(Class<U> enumType) {
            this.enumType = enumType;
        }

        @Override
        public U convert(@NonNull String source) {
            // 调用BaseEnum的静态方法匹配枚举
            return BaseEnum.getByValue(source, enumType);  // 根据编码获取枚举实例
        }
    }
}

