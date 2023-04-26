package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 20:38
 */
public interface AopProxyFactory {

    AopProxy createAopProxy(Object target);
}
