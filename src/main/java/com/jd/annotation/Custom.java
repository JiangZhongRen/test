package com.jd.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Custom {
    @AliasFor("code")
    String value() default "";

    @AliasFor("value")
    String code() default "";
}
