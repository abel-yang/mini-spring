package com.minis.beans;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/28 19:19
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if(singleton == null) {
            BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
            if(beanDefinition == null) {
                throw new BeansException(beanName + "不存在bean定义");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            }  catch (Exception e) {
                e.printStackTrace();
            }
            //注册bean实例
            super.registerSingleton(beanName, singleton);
        }
        return singleton;
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        registerSingleton(beanName, bean);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }
}
