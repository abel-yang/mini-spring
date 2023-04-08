package com.minis.web;

import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:42
 */
public class HandlerMethod {

    private Class<?> beanType;
    private Method method;
    private Object bean;
    private MethodParameter[] parameters;
    private Class<?> returnType;
    private String description;
    private String className;
    private String methodName;


    public HandlerMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
