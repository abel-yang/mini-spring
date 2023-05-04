package com.minis.test.aop;

import java.lang.reflect.Proxy;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/4 08:40
 */
public class ProxyTest {

    public static void main(String[] args) {
        Action action = new ActionB();
        DynamicProxyInvocationHandler proxyDemo = new DynamicProxyInvocationHandler(action);
        Action proxyInstance = (Action)Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), action.getClass().getInterfaces(), proxyDemo);
        proxyInstance.doAction();
    }
}
