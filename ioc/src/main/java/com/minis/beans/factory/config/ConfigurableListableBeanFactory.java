package com.minis.beans.factory.config;

import com.minis.beans.factory.ListableBeanFactory;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:45
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory,
        ConfigurableBeanFactory, AutowireCapableBeanFactory {
}
