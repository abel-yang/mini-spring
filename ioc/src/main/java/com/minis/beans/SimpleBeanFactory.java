package com.minis.beans;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.BeanDefinitionRegistry;
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
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if(singleton == null) {
            BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
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
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        registerSingleton(beanName, bean);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.beanDefinitionMap.get(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionMap.get(beanName).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        if(!bd.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {

            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
