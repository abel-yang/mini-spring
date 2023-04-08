package com.minis.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 11:28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * 映射url
     *
     * @return
     */
    String value() default "";
}
