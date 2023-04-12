package com.minis.web.servlet.view;

import com.minis.web.servlet.View;
import com.minis.web.servlet.ViewResolver;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 13:15
 */
public class InternalResourceViewResolver implements ViewResolver {
    private String viewClassName;
    private Class<?> viewClass;
    private String prefix;
    private String suffix;
    private String contentType;

    public InternalResourceViewResolver() {
        if(getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    @Override
    public View resolverViewName(String viewName) throws Exception {
        return buildView(viewName);
    }


    protected View buildView(String viewName) throws Exception{
        Class<?> viewClass = getViewClass();
        View view = (View) viewClass.newInstance();
        view.setContentType(getContentType());
        view.setUrl(getPrefix() + viewName + getSuffix());
        return view;
    }

    public String getViewClassName() {
        return viewClassName;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        try {
           Class<?> clz = Class.forName(viewClassName);
           setViewClass(clz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Class<?> getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? "" : prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? "" : suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
