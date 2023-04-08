package com.minis.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 17:02
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    private WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod)handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Object bean = handler.getBean();
        Method method = handler.getMethod();
        Object result = null;
        try {
            result = method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().append(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
