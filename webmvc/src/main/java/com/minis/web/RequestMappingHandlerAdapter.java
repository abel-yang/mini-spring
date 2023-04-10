package com.minis.web;

import com.minis.beans.BeansException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 17:02
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    private WebApplicationContext wac;
    private WebBindingInitializer webBindingInitializer;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer)this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod)handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        invokeHandlerMethod(request, response, handler);
    }


    public void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory factory = new WebDataBinderFactory();
        Parameter[] methodParams = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParams.length];
        for(int i = 0; i < methodParams.length; i ++) {
            Object methodParamObj = methodParams[i].getType().newInstance();
            WebDataBinder binder = factory.createBinder(request, methodParamObj, methodParams[i].getName());
            binder.bind(request);
            methodParamObjs[i] = methodParamObj;
        }

        Object bean = handlerMethod.getBean();
        Method method = handlerMethod.getMethod();
        Object returnObj = method.invoke(bean, methodParamObjs);
        try {
            response.getWriter().append(returnObj.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
