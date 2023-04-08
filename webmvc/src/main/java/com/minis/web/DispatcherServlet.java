package com.minis.web;

import com.minis.MappingValue;
import com.minis.XmlConfigReader;
import com.minis.beans.BeansException;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/4 16:16
 */
public class DispatcherServlet extends HttpServlet {
    private List<String> packageNames = new ArrayList<>();
    /**
     * 存储controller名称与对象映射关系
     */
    private Map<String,Object> controllerObjs = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    private List<String> urlMappingNames = new ArrayList<>();
    /**
     * url - obj
     */
    private Map<String, Object> mappingObjs = new HashMap<>();
    private Map<String, Method> mappingMethods = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL path = null;
        try {
            path = this.getServletContext().getResource(contextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(path);
        refresh();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        if(!this.urlMappingNames.contains(path)) {
            return;
        }

        Method method = this.mappingMethods.get(path);
        Object instance = this.mappingObjs.get(path);
        Object result = null;
        try {
            result = method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().append(result.toString());
    }

    protected void refresh() {
        initController();
        initMappings();
    }

    protected void initMappings() {
        for(String controllerName: this.controllerNames) {
            Class<?> clz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clz.getDeclaredMethods();
            for(Method method: methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if(isRequestMapping) {
                    String methodName = method.getName();
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlMapping);
                    this.mappingMethods.put(urlMapping, method);
                    this.mappingObjs.put(urlMapping, obj);
                }
            }
        }
    }

    protected void initController() {
        //扫描包，获取所有类名
        this.controllerNames = scanPackages(this.packageNames);
        for(String name: this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(name);
                this.controllerClasses.put(name, clz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }

            if(clz.isInterface()) {
                continue;
            }

            try {
                obj = clz.newInstance();
                this.controllerObjs.put(name, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for(String packageName: packageNames) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    /**
     * 递归查找包路径下所有类名
     *
     * @param packageName
     * @return
     */
    private Collection<? extends String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        File dir = new File(uri);
        for(File file: dir.listFiles()) {
            if(file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }
}
