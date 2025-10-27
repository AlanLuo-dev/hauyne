package com.luoyx.hauyne.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.function.BiConsumer;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface FullName {


    Class<BiConsumer<?, String>> BI_CONSUMER_CLASS();

//    BiConsumer<Student, String> STUDENT_STRING_BI_CONSUMER
}
