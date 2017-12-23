package com.communicate.module.library.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface RouterPath {
    String provider();
}
