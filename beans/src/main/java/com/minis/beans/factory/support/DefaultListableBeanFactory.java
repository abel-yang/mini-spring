package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:59
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {
    ConfigurableListableBeanFactory parentBeanFactory;


    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class type) {
        List<String> result = new ArrayList<>();
        for(String beanName: this.beanDefinitionNames) {
            BeanDefinition bd = this.getBeanDefinition(beanName);
            Class<?> targetClz = bd.getClass();
            if(type.isAssignableFrom(targetClz)) {
                result.add(beanName);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }



    @Override
    public Object getBean(String beanName) throws BeansException {
        Object result = null;
        if(this.containsBeanDefinition(beanName)) {
            result = super.getBean(beanName);
        }
        if(result == null) {
            result = this.parentBeanFactory.getBean(beanName);
        }
        return result;
    }


    public void setParent(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = (ConfigurableListableBeanFactory) parentBeanFactory;
    }
}
