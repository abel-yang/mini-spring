package com.minis.web;

import com.minis.beans.AbstractPropertyAccessor;
import com.minis.beans.BeanWrapperImpl;
import com.minis.beans.PropertyEditor;
import com.minis.beans.factory.config.PropertyValues;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 09:38
 */
public class WebDataBinder {
    private Object target;
    private Class<?> clz;
    private String objectName;
    private AbstractPropertyAccessor propertyAccessor;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String objectName) {
        this.target = target;
        this.objectName = objectName;
        this.clz = target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }


    public void bind(HttpServletRequest request) {
        PropertyValues pvs = assignParameter(request);
        if(pvs.isEmpty()) {
            throw new IllegalArgumentException("未找到对应参数");
        }
        addBindValues(pvs, request);
        doBind(pvs);
    }

    private void doBind(PropertyValues pvs) {
        applyPropertyValues(pvs);
    }

    protected void applyPropertyValues(PropertyValues pvs) {
        this.propertyAccessor.setPropertyValues(pvs);
    }


    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        this.propertyAccessor.registerCustomEditor(requiredType, propertyEditor);
    }

    protected void addBindValues(PropertyValues pvs, HttpServletRequest request) {
    }

    private PropertyValues assignParameter(HttpServletRequest request) {
        Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(param);
    }



}
