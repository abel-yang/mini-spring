package com.minis.core.env;

import com.minis.beans.util.StringUtils;

import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 14:58
 */
public class MapPropertySource extends EnumerablePropertySource<Map<String, Object>> {

    public MapPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public boolean containsProperty(String name) {
        return this.source.containsKey(name);
    }

    @Override
    public String[] getPropertyNames() {
        return StringUtils.toStringArray(this.source.keySet());
    }

    @Override
    protected Object getProperty(String name) {
        return this.source.get(name);
    }
}
