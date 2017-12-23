package com.communicate.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Action {
    String privider();
}
