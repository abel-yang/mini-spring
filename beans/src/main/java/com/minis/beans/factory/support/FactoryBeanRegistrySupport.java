package com.minis.beans.factory.support;

import com.minis.beans.factory.FactoryBean;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 19:43
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{


    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectForFactoryBean(final  FactoryBean<?> factoryBean, final String beanName) {
        Object object = doGetObjectForFactoryBean(factoryBean, beanName);
        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) {
        return object;
    }

    private Object doGetObjectForFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
