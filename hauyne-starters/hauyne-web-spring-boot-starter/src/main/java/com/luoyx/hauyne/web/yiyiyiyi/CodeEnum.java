package com.luoyx.hauyne.web.yiyiyiyi;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * 枚举统一接口：所有需要嵌套返回的枚举必须实现
 */
public interface CodeEnum<K extends Serializable> extends IEnum<K> {

    String getLabel();

    static <I extends Serializable, R extends Enum<R> & CodeEnum<I>> R codeOf(I value, Class<R> target) {
        for (R enumConstant : target.getEnumConstants()) {
            if (Objects.equals(enumConstant.getValue(), value)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Invalid value value [" + value + "]");
    }

//    static <I extends CodeEnum<? extends Serializable>, R extends Enum<?> & CodeEnum<? extends Serializable>> R codeOf(I source, Class<R> target) {
//        return codeOf(source.getValue(), target);
//    }
//
//    static <C extends Serializable, T extends Enum<?> & CodeEnum<C>> Set<C> codes(Class<T> codeEnumClass) {
//        return Arrays.stream(codeEnumClass.getEnumConstants()).map(CodeEnum::getValue).collect(Collectors.toSet());
//    }
//

}
