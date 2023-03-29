package com.minis.beans.factory.config;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:52
 */
public class PropertyValue {
    private final String name;
    private final Object value;


    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
