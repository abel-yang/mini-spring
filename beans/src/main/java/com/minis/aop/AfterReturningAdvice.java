package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 07:09
 */
public interface AfterReturningAdvice extends AfterAdvice{

    void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable;
}
