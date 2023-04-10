package com.minis.beans;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 10:11
 */
public interface PropertyEditor {
    void setAsText(String text);
    void setValue(Object value);
    Object getValue();
    Object getAsText();
}
