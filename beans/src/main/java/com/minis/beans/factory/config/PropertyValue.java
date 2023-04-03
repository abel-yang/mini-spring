package com.minis.beans.factory.config;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:52
 */
public class PropertyValue {
    private String name;
    private String type;
    private Object value;
    private boolean isRef;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public PropertyValue(String name, String type, Object value, boolean isRef) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }


    public Object getValue() {
        return value;
    }



    public boolean isRef() {
        return isRef;
    }
}
