package com.example.base.cache;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
    String selector() default "";
    boolean checkOrder() default true;
    boolean override() default false;
}
