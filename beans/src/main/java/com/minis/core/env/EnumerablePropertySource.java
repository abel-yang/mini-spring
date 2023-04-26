package com.minis.core.env;

import com.minis.beans.util.ObjectUtils;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 14:40
 */
public abstract class EnumerablePropertySource<T> extends PropertySource<T>{


    public EnumerablePropertySource(String name) {
        super(name);
    }

    public EnumerablePropertySource(String name, T source) {
        super(name, source);
    }

    @Override
    public boolean containsProperty(String name) {
        return ObjectUtils.containsElement(this.getPropertyNames(), name);
    }

    /**
     * 返回所有非null属性名
     *
     * @return
     */
    public abstract String[] getPropertyNames();
}
