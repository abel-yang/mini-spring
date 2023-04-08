package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.Aware;
import com.minis.beans.factory.BeanFactoryAware;
import com.minis.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 14:25
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    protected final List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(32);

    public AbstractBeanFactory() {
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if(singleton == null) {
            //如果没有实例，则尝试从毛胚实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if(singleton == null) {
                //如果连毛胚都没有，则创建bean实例并注册
                BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
                if(beanDefinition == null) {
                    throw new BeansException(beanName + "不存在bean定义");
                }
                singleton = createBean(beanDefinition);
                //注册bean实例
                this.registerBean(beanName, singleton);
                //初始化
                initializeBean(beanDefinition, singleton);
            }

        }
        return singleton;
    }


    private void initializeBean(BeanDefinition beanDefinition, Object bean) throws BeansException {
        invokeAwareMethods(beanDefinition.getId(), bean);
        // 进行beanPostprocessor处理
        // step 1: before
        applyBeanPostProcessorBeforeInitialization(bean, beanDefinition.getId());
        //step 2: init method
        if(beanDefinition.getInitMethodName() != null && !beanDefinition.getInitMethodName().equals("")) {
            invokeInitMethod(beanDefinition, bean);
        }
        //step 3: after
        applyBeanPostProcessorAfterInitialization(bean, beanDefinition.getId());

    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class<?> clz = obj.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            method.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void invokeAwareMethods(final String beanName, final Object bean) throws BeansException {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
    }


    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }

    public abstract Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException;

    public abstract Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException;


    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitionNames.remove(beanDefinition.getId());
        this.beanDefinitionNames.add(beanDefinition.getId());
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        registerSingleton(beanName, bean);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.beanDefinitionMap.get(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionMap.get(beanName).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        if(!bd.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {

            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        //创建毛胚bean实例
        Object bean = doCreateBean(beanDefinition);
        //存放到毛胚实例缓存中
        this.earlySingletonObjects.put(beanDefinition.getId(), bean);
        Class<?> clz = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //完善bean，主要是处理属性
        populateBean(beanDefinition, clz, bean);
        return bean;
    }


    private Object doCreateBean(BeanDefinition bd) {
        Class<?> clz;
        Object bean = null;
        try {
            clz = Class.forName(bd.getClassName());
            ConstructorArgumentValues constructorArgumentValues = bd.getConstructorArgumentValues();

            if(constructorArgumentValues != null && !constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class[constructorArgumentValues.getArgumentCount()];
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];

                for(int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue arg = constructorArgumentValues.getIndexedArgumentValue(i);
                    if(!arg.isRef()) {
                        if("String".equals(arg.getType()) || "java.lang.String".equals(arg.getType())) {
                            paramTypes[i] = String.class;
                            paramValues[i] = arg.getValue();
                        } else if("Integer".equals(arg.getType()) || "java.lang.Integer".equals(arg.getType())) {
                            paramTypes[i] = Integer.class;
                            paramValues[i] = Integer.valueOf((String) arg.getValue());
                        } else if("int".equals(arg.getType()) || "java.lang.int".equals(arg.getType())) {
                            paramTypes[i] = int.class;
                            paramValues[i] = Integer.valueOf((String) arg.getValue());
                        } else {
                            paramTypes[i] = String.class;
                            paramValues[i] = arg.getValue();
                        }
                    } else {
                        //如果是引用类型 则
                        paramTypes[i] = Class.forName(arg.getType());
                        try {
                            paramValues[i] = getBean((String) arg.getValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                Constructor<?> constructor = clz.getConstructor(paramTypes);
                bean = constructor.newInstance(paramValues);
            } else {
                bean = clz.newInstance();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println(bd.getId() + " bean created. " + bd.getClassName() + " : " + bean.toString());
        return bean;
    }

    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object bean) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(propertyValues != null && !propertyValues.isEmpty()) {
            for(int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] paramTypes = new Class[1];

                if(!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    }
                    else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    }
                    else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    }
                    else {
                        paramTypes[0] = String.class;
                    }
                } else {
                    //如果是引用类型 则
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        //再次调用getBean创建ref的bean实例
                        pValue = this.getBean((String) pValue);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }


                Object[] paramValues = new Object[1];
                paramValues[0] = pValue;

                //按照setXxxx规范查找setter方法，调用setter方法设置属性
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                try {
                    Method method = clz.getMethod(methodName, paramTypes);
                    method.invoke(bean, paramValues);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
