package com.minis.beans.factory.xml;

import com.minis.beans.factory.support.SimpleBeanFactory;
import com.minis.beans.factory.config.*;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/28 19:08
 */
public class XmlBeanDefinitionReader {
    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }


    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId,beanClassName);
            //处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues pvs = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for(Element e: propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                boolean isRef = false;
                String pV = "";
                if(pValue != null && !pValue.equals("")) {
                    isRef = false;
                    pV = pValue;
                }
                else if(pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                pvs.addPropertyValue(new PropertyValue(pName, pType, pV, isRef));
            }

            beanDefinition.setPropertyValues(pvs);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            //处理构造参数
            List<Element> constructorElements = element.elements("constructor-arg");
            for(Element e: constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                String aRef = e.attributeValue("ref");
                boolean isRef = false;
                String aV = "";
                if(aValue != null && !aValue.equals("")) {
                    isRef = false;
                    aV = aValue;
                }
                else if(aRef != null && !aRef.equals("")) {
                    isRef = true;
                    aV = aRef;
                    refs.add(aRef);
                }
                avs.addArgumentValue(new ConstructorArgumentValue(aV, aType, aName, isRef));
            }
            beanDefinition.setConstructorArgumentValues(avs);
            this.simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}
