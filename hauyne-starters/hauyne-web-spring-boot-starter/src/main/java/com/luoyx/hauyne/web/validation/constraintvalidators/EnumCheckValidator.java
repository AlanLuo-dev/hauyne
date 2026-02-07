package com.luoyx.hauyne.web.validation.constraintvalidators;


import com.luoyx.hauyne.validation.constraint.EnumCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;

/**
 * 枚举校验器
 *
 * @author Alan.Luo
 */
public class EnumCheckValidator implements ConstraintValidator<EnumCheck, Object> {

    private EnumCheck annotation;

    @Override
    public void initialize(EnumCheck constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Object[] objects = annotation.enumClazz().getEnumConstants();
        try {
            Method method = annotation.enumClazz().getMethod(annotation.getterMethod());
            for (Object o : objects) {
                if (value instanceof Enum) {
                    if (String.valueOf(value).equals(method.invoke(o))) {
                        return true;
                    }
                } else {
                    if (value.equals(method.invoke(o))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}

