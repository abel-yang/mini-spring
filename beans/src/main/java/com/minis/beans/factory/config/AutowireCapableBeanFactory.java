package com.minis.beans.factory.config;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:47
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    /**
     * 初始化前处理
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 初始化后处理
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;



}
