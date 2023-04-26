package com.minis.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 20:39
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("doAction")) {
            System.out.println("代理增强.........");
            return method.invoke(target, args);
        }
        return null;
    }
}
