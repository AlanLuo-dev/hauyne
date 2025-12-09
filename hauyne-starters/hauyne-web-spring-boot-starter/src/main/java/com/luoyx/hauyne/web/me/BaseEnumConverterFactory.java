package com.luoyx.hauyne.web.me;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        private final Map<Serializable, U> codeEnumValues;

        public BaseEnumConverter(Class<U> enumType) {
            this.enumType = enumType;
            this.codeEnumValues = Arrays.stream(enumType.getEnumConstants())
                    .collect(
                            Collectors.toMap(
                                    codeEnum -> Objects.toString(codeEnum.getValue()),
                                    Function.identity(),
                                    (ignored, v2) -> v2
                            )
                    );
        }

        /**
         * 该convert方法下，切不可抛异常， 否则会导致 枚举常量匹配生效！这是我们不希望的
         * 枚举常量匹配生效 是 调用 org.springframework.beans.TypeConverterDelegate#attemptToConvertStringToEnum 方法生效的
         * 故而，传入的source在codeEnumValues中不存在时，应该返回null
         *
         * @param source
         * @return
         */
        @Override
        public U convert(@NonNull String source) {
            // 调用BaseEnum的静态方法匹配枚举
//            return BaseEnum.getByValue(source, enumType);  // 根据编码获取枚举实例
            return this.codeEnumValues.get(source);
        }
    }
}

