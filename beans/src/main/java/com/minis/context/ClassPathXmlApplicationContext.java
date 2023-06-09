package com.minis.context;

import com.minis.beans.BeansException;
import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.DefaultListableBeanFactory;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/28 18:31
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{
    DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String filename)  {
        this(filename, true);

    }

    public ClassPathXmlApplicationContext(String filename, boolean isRefresh)  {
        try {
            Resource resource = new ClassPathXmlResource(filename);
            DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
            XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
            reader.loadBeanDefinitions(resource);
            this.beanFactory = beanFactory;
            if(isRefresh) {
                refresh();
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void registerListeners() {
        String[] bdNames = this.beanFactory.getBeanDefinitionNames();
        for (String bdName : bdNames) {
            Object bean = null;
            try {
                bean = getBean(bdName);
            } catch (BeansException e1) {
                e1.printStackTrace();
            }

            if (bean instanceof ApplicationListener) {
                this.getApplicationEventPublisher().addApplicationListener((ApplicationListener<?>) bean);
            }
        }
    }

    @Override
    protected void initApplicationEventPublisher() {
        SimpleApplicationEventPublisher eventPublisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(eventPublisher);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setBeanFactory(beanFactory);

        beanFactory.addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }


    @Override
    protected void onRefresh() { this.beanFactory.refresh(); }



}
