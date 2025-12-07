package com.luoyx.hauyne.web.yiyiyiyi;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public class CodeEnumConverterFactory<R extends CodeEnum<? extends Serializable>> implements ConverterFactory<String, R> {

    /**
     * Get the converter to convert from S to target type T, where T is also an instance of R.
     *
     * @param targetType the target type to convert to
     * @return a converter from S to T
     */
    @NonNull
    @Override
    public <T extends R> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new CodeEnumConvert<>(targetType);
    }
}
