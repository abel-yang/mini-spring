package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 18:58
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
}
