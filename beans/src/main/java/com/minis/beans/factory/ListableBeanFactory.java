package com.minis.beans.factory;

import com.minis.beans.BeansException;

import java.util.Map;

/**
 * 将 Factory 内部管理的 Bean 作为一个集合来对待，
 * 获取 Bean 的数量，得到所有 Bean 的名字，
 * 按照某个类型获取 Bean 列表
 *
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:38
 */
public interface ListableBeanFactory extends BeanFactory{

    /**
     * 是否包含指定beanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取bean定义数量
     *
     * @return
     */
    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    /**
     * 获取指定类型bean map
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
