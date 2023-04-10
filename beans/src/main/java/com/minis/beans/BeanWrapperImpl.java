package com.minis.beans;

import com.minis.beans.factory.config.PropertyValue;
import com.minis.beans.factory.config.PropertyValues;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 10:05
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport{
    /**
     * 目标对象
     */
    private Object wrappedObject;
    private Class<?> clz;
    /**
     * 参数值
     */
    private PropertyValues pvs;

    public BeanWrapperImpl(Object wrappedObject) {
        //不同数据类型的参数转换器editor
        registerDefaultEditors();
        this.wrappedObject = wrappedObject;
        this.clz = wrappedObject.getClass();
    }

    /**
     * 绑定参数值
     *
      * @param pvs
     */
    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    public void setPropertyValue(PropertyValue pv) {
        //拿到参数处理器
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        //找到对该参数类型的editor
        PropertyEditor pe = this.findCustomEditor(propertyHandler.getPropertyClz());
        if(pe == null) {
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        //设置参数值
        pe.setAsText((String) pv.getValue());
        propertyHandler.setValue(pe.getValue());
    }

    /**
     * 一个内部类，用于处理参数，通过getter()和setter()操作属性
     */
    class BeanPropertyHandler {
        Method writeMethod;
        Method readMethod;
        Class<?> propertyClz;

        public BeanPropertyHandler(String propertyName) {
            try {
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                //获取属性方法
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() +
                        propertyName.substring(1), propertyClz);
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() +
                        propertyName.substring(1), propertyClz);
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public Object getValue() {
            Object result = null;
            readMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
