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
    private PointcutAdvisor advisor;

    public JdkDynamicAopProxy(Object target, PointcutAdvisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> targetClass = (target != null ? target.getClass() : null);
        if(this.advisor.getPointcut().getMethodMatcher().matches(method, targetClass)) {
            MethodInterceptor methodInterceptor = this.advisor.getMethodInterceptor();
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            return methodInterceptor.invoke(invocation);
        }
        return method.invoke(target, args);
    }
}
