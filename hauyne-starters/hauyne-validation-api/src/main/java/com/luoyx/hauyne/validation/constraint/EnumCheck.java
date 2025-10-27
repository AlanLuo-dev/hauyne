package com.luoyx.hauyne.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值校验注解：用于校验枚举值的合法性
 *
 * @author @author Alan.Luo
 */
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(EnumCheck.List.class)
public @interface EnumCheck {
    String message() default "{hauyne.validation.constraints.EnumCheck.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举类（数据的合法范围）
     *
     * @return Class
     */
    Class<?> enumClazz();

    /**
     * the method's name ,which used to validate the enum's value
     *
     * @return method's name
     */
    String getterMethod() default "ordinal";

    /**
     * Defines several {@link EnumCheck} annotations on the same element.
     *
     * @see EnumCheck
     */
    @Documented
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @interface List {
        EnumCheck[] value();
    }
}
