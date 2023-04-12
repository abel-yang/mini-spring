package com.minis.web.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 11:18
 */
public class ModelAndView {

    private Object view;
    private Map<String, Object> model = new HashMap<>();


    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }
    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(String viewName, Map<String, ?> modelData) {
        this.view = viewName;
        addAllAttribute(modelData);
    }

    public ModelAndView(View view, Map<String, ?> modelData) {
        this.view = view;
        addAllAttribute(modelData);
    }

    public ModelAndView(String viewName, String modelName, Object modelObject) {
        this.view = viewName;
        addObject(modelName, modelObject);
    }

    public ModelAndView(View view, String modelName, Object modelObject) {
        this.view = view;
        addObject(modelName, modelObject);
    }

    public void setViewName(String viewName) {
        this.view = viewName;
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }

    private void addAllAttribute(Map<String,?> modelData) {
        if(modelData != null) {
            model.putAll(modelData);
        }
    }

    public View getView() {
        return (this.view instanceof View ? (View) this.view : null);
    }

    public boolean hasView() {
        return (this.view != null);
    }

    public boolean isReference() {    return (this.view instanceof String);  }

    public Map<String, Object> getModel() {    return this.model;  }

    public void addAttribute(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        addAttribute(attributeName, attributeValue);
        return this;
    }
}
