package com.minis.test.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 07:18
 */

import com.minis.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("----------my interceptor after method call----------");
    }
}
