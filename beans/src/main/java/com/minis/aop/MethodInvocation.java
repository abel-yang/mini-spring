package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/1 11:40
 */
public interface MethodInvocation {

    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed() throws Throwable;
}
