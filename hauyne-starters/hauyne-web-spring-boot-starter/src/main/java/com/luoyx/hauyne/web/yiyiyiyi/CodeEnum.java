package com.luoyx.hauyne.web.yiyiyiyi;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 枚举统一接口：所有需要嵌套返回的枚举必须实现
 */
public interface CodeEnum<C extends Serializable> extends IEnum<C>, Code<C> {

    static <I extends Serializable, R extends Enum<?> & CodeEnum<? extends Serializable>> R codeOf(I code, Class<R> target) {
        for (R enumConstant : target.getEnumConstants()) {
            if (Objects.equals(enumConstant.getCode(), code)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Invalid code value [" + code + "]");
    }

    static <I extends CodeEnum<? extends Serializable>, R extends Enum<?> & CodeEnum<? extends Serializable>> R codeOf(I source, Class<R> target) {
        return codeOf(source.getCode(), target);
    }

    static <C extends Serializable, T extends Enum<?> & CodeEnum<C>> Set<C> codes(Class<T> codeEnumClass) {
        return Arrays.stream(codeEnumClass.getEnumConstants()).map(CodeEnum::getCode).collect(Collectors.toSet());
    }

    @Override
    @JsonValue
    default C getValue() {
        return this.getCode();
    }

    default String getMsg() {
        return Objects.toString(this.getCode(), StringUtils.EMPTY);
    }
}
