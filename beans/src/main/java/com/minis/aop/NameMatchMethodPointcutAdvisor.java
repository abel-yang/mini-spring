package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 19:06
 */
public class NameMatchMethodPointcutAdvisor implements PointcutAdvisor{
    private Advice advice;
    private String mappedName;
    private MethodInterceptor mi;
    private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

    public NameMatchMethodPointcutAdvisor() {
    }

    public NameMatchMethodPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.mi;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.mi = methodInterceptor;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
        if(advice instanceof BeforeAdvice) {
            mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice);
        }
        else if(advice instanceof AfterAdvice) {
            mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice);
        }
        else if(advice instanceof MethodInterceptor) {
            mi = (MethodInterceptor) advice;
        }
        setMethodInterceptor(mi);
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
        this.pointcut.setMappedName(mappedName);
    }
}
