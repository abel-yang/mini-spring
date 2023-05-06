package com.minis.beans.factory.config;

import com.minis.beans.BeansException;
import com.minis.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 14:20
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor beanPostProcessor: this.beanPostProcessors) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
             if(current == null) {
                 return result;
             }
             result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor beanPostProcessor: this.beanPostProcessors) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if(current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }


}
