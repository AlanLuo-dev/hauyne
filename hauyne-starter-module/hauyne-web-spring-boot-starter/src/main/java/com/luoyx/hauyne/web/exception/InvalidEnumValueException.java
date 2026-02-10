package com.luoyx.hauyne.web.exception;


import com.luoyx.hauyne.api.enums.core.EnumDefinition;

public class InvalidEnumValueException extends RuntimeException {

    private final String inputValue;
    private final Class<?> enumType;

    public InvalidEnumValueException(String inputValue, Class<?> enumType) {
        super(buildMessage(inputValue, enumType));
        this.inputValue = inputValue;
        this.enumType = enumType;
    }

    private static String buildMessage(String inputValue, Class<?> enumType) {

        String enumName = enumType.getSimpleName();

        if (EnumDefinition.class.isAssignableFrom(enumType)) {
            EnumDefinition<?, ?>[] constants = (EnumDefinition<?, ?>[]) enumType.getEnumConstants();
            if (constants != null && constants.length > 0) {
                enumName = constants[0].getEnumName();
            }
        }

        String format = String.format(
                "【%s】不是合法的%s",
                inputValue,
                enumName
        );
        return format;
    }

    public String getInputValue() {
        return inputValue;
    }

    public Class<?> getEnumType() {
        return enumType;
    }
}

