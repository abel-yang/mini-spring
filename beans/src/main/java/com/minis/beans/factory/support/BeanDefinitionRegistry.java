package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanDefinition;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 11:31
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册bean定义
     *
     * @param name
     * @param bd
     */
    void registerBeanDefinition(String name, BeanDefinition bd);

    /**
     * 移除
     *
     * @param name
     */
    void removeBeanDefinition(String name);

    /**
     * 获取
     *
     * @param name
     * @return
     */
    BeanDefinition getBeanDefinition(String name);

    /**
     * 是否包含指定bean
     *
     * @param name
     * @return
     */
    boolean containsBeanDefinition(String name);
}
