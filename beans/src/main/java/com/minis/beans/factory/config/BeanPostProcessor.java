package com.minis.beans.factory.config;

import com.minis.beans.BeansException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 14:18
 */
public interface BeanPostProcessor {
    /**
     * 初始化前处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 初始化后处理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
