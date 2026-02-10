package com.luoyx.hauyne.web.enums.validate;


import com.luoyx.hauyne.api.enums.core.EnumDefinition;

import java.util.HashMap;
import java.util.Map;

public final class EnumDefValidator {

    private EnumDefValidator() {
    }

    public static void validate(Class<? extends Enum> enumClass) {

        if (!EnumDefinition.class.isAssignableFrom(enumClass)) {
            return;
        }

        Enum<?>[] constants = enumClass.getEnumConstants();
        if (constants == null || constants.length == 0) {
            return;
        }

        Map<Object, Enum<?>> valueMap = new HashMap<>();

        for (Enum<?> e : constants) {
            EnumDefinition<?, ?> enumDefinition = (EnumDefinition<?, ?>) e;
            Object value = enumDefinition.getValue();

            Enum<?> existed = valueMap.putIfAbsent(value, e);
            if (existed != null) {
                throw new IllegalStateException(
                        String.format(
                                "枚举 %s 中存在重复的 value 定义：[%s]，冲突常量：%s / %s",
                                enumClass.getName(),
                                value,
                                existed.name(),
                                e.name()
                        )
                );
            }
        }
    }
}

