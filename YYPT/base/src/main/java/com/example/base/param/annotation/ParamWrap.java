package com.example.base.param.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
@Component
public @interface ParamWrap {
}
