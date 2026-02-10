package com.luoyx.hauyne.web.enums.convert;

import com.luoyx.hauyne.api.enums.core.EnumDefinition;
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
public class EnumDefConverterFactory<R extends Enum<R> & EnumDefinition<? extends Serializable, R>>
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
    private static class BaseEnumConverter<U extends Enum<U> & EnumDefinition<? extends Serializable, U>>
            implements Converter<String, U> {

        /**
         * 枚举类型
         */
        private final Class<U> enumType;

        /**
         * 枚举值-枚举对象 Map映射
         */
        private final Map<Serializable, U> codeEnumMap;

        public BaseEnumConverter(Class<U> enumType) {
            this.enumType = enumType;
            this.codeEnumMap = Arrays.stream(enumType.getEnumConstants())
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
         * @param source 输入值
         * @return 转换后的枚举对象
         */
        @Override
        public U convert(@NonNull String source) {
            U result = this.codeEnumMap.get(source);
            if (result == null) {

                // 获取枚举的业务名称
                final String enumName = enumType.getEnumConstants()[0].getEnumName();

                // 添加到 ThreadLocal
                EnumConvertContext.add(enumName, source);
            }
            return result;
        }
    }
}

