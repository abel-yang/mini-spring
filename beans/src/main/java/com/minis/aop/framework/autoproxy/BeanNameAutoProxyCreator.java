package com.minis.aop.framework.autoproxy;

import com.minis.aop.AopProxyFactory;
import com.minis.aop.DefaultAopProxyFactory;
import com.minis.aop.PointcutAdvisor;
import com.minis.aop.ProxyFactoryBean;
import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.BeanFactoryAware;
import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.util.PatternMatchUtils;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 19:57
 */
public class BeanNameAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {
    private String pattern;
    private BeanFactory beanFactory;
    private AopProxyFactory aopProxyFactory;
    private String interceptorName;
    private PointcutAdvisor advisor;

    public BeanNameAutoProxyCreator() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(isMatch(beanName, pattern)) {
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(bean);
            proxyFactoryBean.setTargetName(beanName);
            proxyFactoryBean.setAopProxyFactory(this.aopProxyFactory);
            proxyFactoryBean.setInterceptorName(interceptorName);
            proxyFactoryBean.setBeanFactory(beanFactory);
            return proxyFactoryBean;
        }
        return bean;
    }

    private boolean isMatch(String beanName, String pattern) {
        return PatternMatchUtils.simpleMatch(pattern, beanName);
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setAdvisor(PointcutAdvisor advisor) {
        this.advisor = advisor;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }
}
