package com.minis.context;

import com.minis.beans.BeansException;
import com.minis.beans.factory.ListableBeanFactory;
import com.minis.beans.factory.annotation.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.AutowireCapableBeanFactory;
import com.minis.core.env.Environment;
import com.minis.core.env.EnvironmentCapable;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 16:56
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ApplicationEventPublisher{

    /**
     * 获取应用名称
     *
     * @return
     */
    String getApplicationName();

    /**
     * 获取启动曰期
     *
     * @return
     */
    long getStartupDate();

    /**
     * 获取beanFactory
     *
     * @return
     * @throws IllegalStateException
     */
    AutowireCapableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
