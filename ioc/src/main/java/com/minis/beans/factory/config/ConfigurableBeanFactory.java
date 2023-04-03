package com.minis.beans.factory.config;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.annotation.BeanPostProcessor;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:42
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加bean处理器
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 注册bean依赖
     *
     * @param beanName
     * @param dependentBeanName
     */
    void registerDependentBean(String beanName, String dependentBeanName);

    /**
     * 获取bean依赖bean集合
     *
     * @param beanName
     * @return
     */
    String[] getDependentBeans(String beanName);

    /**
     * 获取bean依赖
     *
     * @param beanName
     * @return
     */
    String[] getDependenciesForBean(String beanName);
}
