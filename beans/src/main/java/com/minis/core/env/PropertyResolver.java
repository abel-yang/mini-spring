package com.minis.core.env;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:52
 */
public interface PropertyResolver {
    String getProperty(String key);
    String getProperty(String key, String defaultValue);
    String getRequiredProperty(String key) throws IllegalStateException;
    boolean containsProperty(String key);
    String resolvePlaceholders(String text);
    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;

}
