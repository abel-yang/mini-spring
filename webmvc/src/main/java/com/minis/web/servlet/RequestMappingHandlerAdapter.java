package com.minis.web.servlet;

import com.minis.web.HttpMessageConverter;
import com.minis.web.bind.annotation.ResponseBody;
import com.minis.web.WebDataBinder;
import com.minis.web.WebDataBinderFactory;
import com.minis.web.bind.support.WebBindingInitializer;
import com.minis.web.servlet.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 17:02
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private WebBindingInitializer webBindingInitializer;
    private HttpMessageConverter httpMessageConverter;



    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod)handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {
        return invokeHandlerMethod(request, response, handler);
    }


    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        ModelAndView mav = null;
        WebDataBinderFactory factory = new WebDataBinderFactory();
        Parameter[] methodParams = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParams.length];
        for(int i = 0; i < methodParams.length; i ++) {
            Object methodParamObj = methodParams[i].getType().newInstance();
            WebDataBinder binder = factory.createBinder(request, methodParamObj, methodParams[i].getName());
            webBindingInitializer.initBinder(binder);
            binder.bind(request);
            methodParamObjs[i] = methodParamObj;
        }

        Object bean = handlerMethod.getBean();
        Method method = handlerMethod.getMethod();
        Object returnObj = method.invoke(bean, methodParamObjs);
        if(handlerMethod.isAnnotationPresent(ResponseBody.class)) {
            httpMessageConverter.write(returnObj, response);
        }
        else {
            //返回前端页面
            if(returnObj instanceof ModelAndView) {
                mav = (ModelAndView) returnObj;
            }
            else if(returnObj instanceof String) {
                String targetViewName = (String) returnObj;
                mav = new ModelAndView();
                mav.setViewName(targetViewName);
            }
        }
        return mav;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public void setHttpMessageConverter(HttpMessageConverter httpMessageConverter) {
        this.httpMessageConverter = httpMessageConverter;
    }
}
