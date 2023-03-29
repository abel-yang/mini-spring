package com.minis.beans.factory.config;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:53
 */
public class ArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
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
}
