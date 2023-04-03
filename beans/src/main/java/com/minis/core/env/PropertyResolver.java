package com.minis.core.env;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:52
 */
public interface PropertyResolver {
    /**
     * 是否包含指定名称属性
     *
     * @param key
     * @return
     */
    boolean containsProperty(String key);

    /**
     * 根据key获取属性
     *
     * @param key
     * @return
     */
    String getProperty(String key);

    /**
     * 根据key获取属性,无则返回默认值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    String getProperty(String key, String defaultValue);

    /**
     * 根据key获取属性, 指定类型
     *
     * @param key
     * @param targetType
     * @param <T>
     * @return
     */
    <T> T getProperty(String key, Class<T> targetType);

    /**
     * 根据key获取属性, 指定类型， 无则返回默认值
     *
     * @param key
     * @param targetType
     * @param defaultValue
     * @param <T>
     * @return
     */
    <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    /**
     * 获取属性类型
     *
     * @param key
     * @param targetType
     * @param <T>
     * @return
     */
    <T> Class<T> getPropertyAsClass(String key, Class<T> targetType);

    /**
     * 获取属性，没有抛异常
     *
     * @param key
     * @return
     * @throws IllegalStateException
     */
    String getRequiredProperty(String key) throws IllegalStateException;

    /**
     * 获取指定类型属性，没有抛异常
     *
     * @param key
     * @param targetType
     * @param <T>
     * @return
     * @throws IllegalStateException
     */
    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    /**
     * 解析占位符
     *
     * @param text
     * @return
     */
    String resolvePlaceholders(String text);

    /**
     * 解析占位符
     *i
     * @param text
     * @return
     * @throws IllegalArgumentException
     */
    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
}
