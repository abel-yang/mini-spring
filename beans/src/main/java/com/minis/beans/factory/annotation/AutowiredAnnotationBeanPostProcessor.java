package com.minis.beans.factory.annotation;

import com.minis.beans.BeansException;
import com.minis.beans.factory.Aware;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.BeanFactoryAware;
import com.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Field;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 14:19
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();
        for(Field field: fields) {
            boolean isAutowired = field.isAnnotationPresent(Autowired.class);
            if(isAutowired) {
                Object autowireObj = this.beanFactory.getBean(field.getName());
                field.setAccessible(true);
                try {
                    field.set(bean, autowireObj);
                    System.out.println("autowire " + field.getName() + " for bean " + beanName);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if(!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException("AutowiredAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory: " + beanFactory);
        }
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }


}
