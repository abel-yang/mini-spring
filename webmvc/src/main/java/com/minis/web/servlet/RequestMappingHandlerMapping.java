package com.minis.web.servlet;

import com.minis.web.servlet.handler.MappingRegistry;
import com.minis.web.bind.annotation.RequestMapping;
import com.minis.web.context.WebApplicationContext;
import com.minis.web.servlet.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:47
 */
public class RequestMappingHandlerMapping implements HandlerMapping {
    private WebApplicationContext wac;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    private void initMapping() {
        Class<?> clz;
        Object obj;
        String[] controllerNames =  this.wac.getBeanDefinitionNames();
        for(String controllerName: controllerNames) {
            try {
                clz = Class.forName(controllerName);
                obj = this.wac.getBean(controllerName);
            }  catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Method[] methods = clz.getDeclaredMethods();
            for(Method method: methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if(isRequestMapping) {
                    String methodName = method.getName();
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                    this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                    this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                }
            }
        }
    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String path = request.getRequestURI();
        if(!this.mappingRegistry.getUrlMappingNames().contains(path)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(path);
        Object bean = this.mappingRegistry.getMappingObjs().get(path);
        return new HandlerMethod(method, bean);
    }
}
