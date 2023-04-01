package com.minis.beans.factory.config;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:53
 */
public class ConstructorArgumentValue {
    private Object value;
    private String type;
    private String name;
    private boolean isRef;

    public ConstructorArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ConstructorArgumentValue(Object value, String type, String name, boolean isRef) {
        this.value = value;
        this.type = type;
        this.name = name;
        this.isRef = isRef;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRef() {
        return isRef;
    }
}
