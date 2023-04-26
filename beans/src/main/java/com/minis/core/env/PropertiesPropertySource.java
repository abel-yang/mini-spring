package com.minis.core.env;

import java.util.Map;
import java.util.Properties;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/24 15:03
 */
public class PropertiesPropertySource extends MapPropertySource{

    public PropertiesPropertySource(String name, Properties properties) {
        super(name, (Map)properties);
    }

    protected PropertiesPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        synchronized (this.source) {
            return super.getPropertyNames();
        }
    }
}
