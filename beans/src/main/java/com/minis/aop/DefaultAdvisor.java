package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/1 14:10
 */
public class DefaultAdvisor implements Advisor{

    private MethodInterceptor methodInterceptor;

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
