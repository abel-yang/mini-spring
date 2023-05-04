package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 07:13
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice{
    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        this.advice.afterReturning(proceed, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return proceed;
    }
}
