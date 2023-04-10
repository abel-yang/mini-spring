package com.minis.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 19:52
 */
public class WebDataBinderFactory {

    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder binder = new WebDataBinder(target, objectName);
        initBinder(binder, request);
        return binder;
    }

    protected void initBinder(WebDataBinder binder, HttpServletRequest request) {

    }
}
