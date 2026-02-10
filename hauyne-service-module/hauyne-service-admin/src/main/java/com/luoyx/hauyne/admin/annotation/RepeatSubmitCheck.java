package com.luoyx.hauyne.admin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防重复点击注解
 * 默认1s
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmitCheck {

    /**
     * 锁的key
     *
     * @return 锁的key
     */
    String lockKey();

    /**
     * 加锁时间
     *
     * @return 加锁时间
     */
    int lockTime() default 1;

    /**
     * 是否需要续期 默认不需要续期
     *
     * @return true=需要续期 false=不需要续期
     */
    boolean needRenewal() default false;
}


