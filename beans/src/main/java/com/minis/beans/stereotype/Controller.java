package com.minis.beans.stereotype;

import java.lang.annotation.*;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/10 10:42
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
