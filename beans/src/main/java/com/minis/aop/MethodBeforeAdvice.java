package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 07:06
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] arguments, Object target) throws Throwable;
}
