package com.luoyx.hauyne.web.yiyiyiyi;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CodeEnumConvert <T extends CodeEnum<? extends Serializable>> implements Converter<String, T> {

    private final Map<Serializable, T> codeEnumValues;

    public CodeEnumConvert(Class<T> codeEnumClass) {
        this.codeEnumValues = Arrays.stream(codeEnumClass.getEnumConstants())
                .collect(
                        Collectors.toMap(
                                codeEnum -> Objects.toString(codeEnum.getValue()),
                                Function.identity(),
                                (ignored, v2) -> v2
                        )
                );
    }

    @Override
    public T convert(@NonNull String source) {
        return this.codeEnumValues.get(source);
    }
}
