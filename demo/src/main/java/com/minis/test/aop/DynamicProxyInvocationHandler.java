package com.minis.test.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/4 08:37
 */
public class DynamicProxyInvocationHandler implements InvocationHandler {

    private Object target;

    public DynamicProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("..............before method call");
        Object result = method.invoke(target, args);
        System.out.println("..............after method call");
        return result;
    }
}
