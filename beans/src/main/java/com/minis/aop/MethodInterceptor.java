package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/1 11:39
 */
public interface MethodInterceptor extends Interceptor{

    Object invoke(MethodInvocation invocation) throws Throwable;
}
