package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/1 14:05
 */
public interface Advisor {

    MethodInterceptor getMethodInterceptor();

    void setMethodInterceptor(MethodInterceptor methodInterceptor);
}
