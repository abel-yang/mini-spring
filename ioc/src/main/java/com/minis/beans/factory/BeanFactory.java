package com.minis.beans.factory;

import com.minis.beans.BeansException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/28 18:59
 */
public interface BeanFactory {

    /**
     * 根据beanName获取bean实例
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 注册bean
     *
     * @param beanName
     * @param bean
     */
    void registerBean(String beanName, Object bean);

    /**
     * 是否包含指定bean
     *
     * @param beanName
     * @return
     */
    Boolean containsBean(String beanName);

    /**
     * 是否单例
     * @param beanName
     * @return
     */
    boolean isSingleton(String beanName);

    /**
     * 是否多例
     *
     * @param beanName
     * @return
     */
    boolean isPrototype(String beanName);

    /**
     * 获取bean class
     *
     * @param beanName
     * @return
     */
    Class<?> getType(String beanName);
}
