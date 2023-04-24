package com.minis.lang;

import javafx.beans.binding.When;

import java.lang.annotation.*;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 09:47
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nullable {
}
