package com.minis.beans.factory.config;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 09:09
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例bean
     *
     * @param beanName
     * @param bean
     */
    void registerSingleton(String beanName, Object bean);

    /**
     * 获取单例bean
     *
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 是否包含指定bean
     * @param beanName
     * @return
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例bean names
     * @return
     */
    String[] getSingletonNames();

}
