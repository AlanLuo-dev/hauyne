package com.luoyx.hauyne.web.exception;


import com.luoyx.hauyne.web.enums.core.EnumDef;

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

        if (EnumDef.class.isAssignableFrom(enumType)) {
            EnumDef<?, ?>[] constants = (EnumDef<?, ?>[]) enumType.getEnumConstants();
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

