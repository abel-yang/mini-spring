package com.minis.web;

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

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String objectName) {
        this.target = target;
        this.objectName = objectName;
        this.clz = target.getClass();
    }


    public void bind(HttpServletRequest request) {
        PropertyValues pvs = assignParameter(request);
        addBindValues(pvs, request);
        doBind(pvs);
    }

    private void doBind(PropertyValues pvs) {
        applyPropertyValues(pvs);
    }

    protected void applyPropertyValues(PropertyValues pvs) {
        getPropertyAccessor().setPropertyValues(pvs);
    }


    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

    protected BeanWrapperImpl getPropertyAccessor() {
        return new BeanWrapperImpl(this.target);
    }

    protected void addBindValues(PropertyValues pvs, HttpServletRequest request) {
    }

    private PropertyValues assignParameter(HttpServletRequest request) {
        Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(param);
    }



}
