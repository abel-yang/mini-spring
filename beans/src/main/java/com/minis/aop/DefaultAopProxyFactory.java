package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 20:42
 */
public class DefaultAopProxyFactory implements AopProxyFactory{

    @Override
    public AopProxy createAopProxy(Object target) {
        return new JdkDynamicAopProxy(target);
    }
}
