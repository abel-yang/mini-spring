package com.minis.beans.factory;

import com.minis.beans.BeansException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/3 09:34
 */
public interface BeanFactoryAware extends Aware{

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
     * @param beanFactory owning BeanFactory (never {@code null}).
     * The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     * @see BeanInitializationException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
