package com.minis.beans.factory;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 19:42
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
