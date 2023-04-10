package com.minis.beans;

import com.minis.beans.util.NumberUtils;
import com.minis.beans.util.StringUtils;

import java.text.NumberFormat;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 10:12
 */
public class CustomNumberEditor implements PropertyEditor{
    private Class<? extends Number> numberClass;
    private NumberFormat numberFormat;
    private boolean allowEmpty;
    private Object value;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * 将字符串转number
     *
     * @param text
     */
    @Override
    public void setAsText(String text) {
        if(this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        }
        else if(numberFormat != null) {
            setValue(NumberUtils.parseNumber(text, numberClass, numberFormat));
        }
        else {
            setValue(NumberUtils.parseNumber(text, numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof Number) {
            this.value = NumberUtils.convertNumberToTargetClass((Number)value, numberClass);
        } else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getAsText() {
        if(this.value == null) {
            return "";
        }
        if(this.numberFormat != null) {
            return this.numberFormat.format(value);
        }
        return value.toString();
    }
}
