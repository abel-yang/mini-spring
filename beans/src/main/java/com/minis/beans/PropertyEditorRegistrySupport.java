package com.minis.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 10:55
 */
public class PropertyEditorRegistrySupport {

    private Map<Class<?>, PropertyEditor> defaultEditors;
    private Map<Class<?>, PropertyEditor> customEditors;

    /**
     * 注册默认转换器
     */
    protected void registerDefaultEditors() {
        createDefaultEditors();
    }

    public PropertyEditor getDefaultEditor(Class<?> requiredType) {
        return defaultEditors.get(requiredType);
    }

    private void createDefaultEditors() {
        this.defaultEditors = new HashMap<>(64);
        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
        this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
        this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
        this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
        this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
        this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
        this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
        this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
        this.defaultEditors.put(String.class, new StringEditor(String.class, true));
    }

    /**
     * 注册客户化转换器
     *
     * @param requiredType
     * @param propertyEditor
     */
    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        if(this.customEditors == null) {
            this.customEditors = new LinkedHashMap<>(16);
        }
        this.customEditors.put(requiredType, propertyEditor);
    }

    /**
     * 查找客户化转换器
     *
     * @param requiredType
     * @return
     */
    public PropertyEditor findCustomEditor(Class<?> requiredType) {
        return this.customEditors.get(requiredType);
    }

    public boolean hasCustomEditorForElement(Class<?> elementType) {
        return (elementType != null && this.customEditors != null && this.customEditors.containsKey(elementType));
    }


    private PropertyEditor getCustomEditor(Class<?> requiredType) {
        if (requiredType == null || this.customEditors == null) {
            return null;
        }
        return this.customEditors.get(requiredType);
    }
}
