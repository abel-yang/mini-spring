package com.minis.web;

import com.minis.beans.BeansException;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.annotation.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;
import com.minis.context.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 14:35
 */
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext{
    private WebApplicationContext parentApplicationContext;
    private ServletContext servletContext;
    private DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public AnnotationConfigWebApplicationContext(String filename) {
        this(filename, null);
    }

    public AnnotationConfigWebApplicationContext(String filename, WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.servletContext = this.parentApplicationContext.getServletContext();
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = this.scanPackages(packageNames);
        this.beanFactory = new DefaultListableBeanFactory();
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
        loaderBeanDefinition(controllerNames);
        try {
            refresh();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private void loaderBeanDefinition(List<String> controllerNames) {
        for(String controller: controllerNames) {
            BeanDefinition beanDefinition = new BeanDefinition(controller, controller);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }

    public void setParent(WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener<? extends ApplicationEvent> listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    protected void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void finishRefresh() {

    }


    @Override
    public void registerListeners() {
        SimpleApplicationListener listener = new SimpleApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
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
