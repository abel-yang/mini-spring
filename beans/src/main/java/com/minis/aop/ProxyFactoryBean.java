package com.minis.aop;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.BeanFactoryAware;
import com.minis.beans.factory.FactoryBean;
import com.minis.beans.util.ClassUtils;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 20:46
 */
public class ProxyFactoryBean implements FactoryBean<Object> , BeanFactoryAware {

    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private Object singletonInstance;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private BeanFactory beanFactory;
    private String interceptorName;
    private Advisor advisor;
    private volatile boolean isInitialized = false;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public Object getObject() throws Exception {
        if(!this.isInitialized) {
            this.isInitialized = true;
            initializeAdvisor();
        }
        return getSingletonInstance();
    }


    private synchronized void initializeAdvisor() {
        MethodInterceptor mi = null;
        try {
            Object advice = this.beanFactory.getBean(this.interceptorName);
            if(advice instanceof BeforeAdvice) {
                mi = new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice);
            }
            else if(advice instanceof AfterAdvice) {
                mi = new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice);
            }
            else if(advice instanceof MethodInterceptor) {
                mi = (MethodInterceptor) advice;
            }

            advisor = new DefaultAdvisor();
            advisor.setMethodInterceptor(mi);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private synchronized Object getSingletonInstance() {
        if(this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    private Object getProxy(AopProxy aopProxy) {
        return aopProxy.getObject();
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target, advisor);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public String[] getInterceptorNames() {
        return interceptorNames;
    }

    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
