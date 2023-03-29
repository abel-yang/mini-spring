package com.minis.beans.factory.xml;

import com.minis.beans.SimpleBeanFactory;
import com.minis.beans.factory.config.*;
import com.minis.core.Resource;
import com.minis.beans.factory.BeanFactory;
import org.dom4j.Element;

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
            for(Element e: propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                pvs.addPropertyValue(new PropertyValue(pType, pName, pValue));
            }

            beanDefinition.setPropertyValues(pvs);

            ArgumentValues avs = new ArgumentValues();
            //处理构造参数
            List<Element> constructorElements = element.elements("constructor-arg");
            for(Element e: constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                avs.addArgumentValue(new ArgumentValue(aType, aName, aValue));
            }
            beanDefinition.setConstructorArgumentValues(avs);
            this.simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}
