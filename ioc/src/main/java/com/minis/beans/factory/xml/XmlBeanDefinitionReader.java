package com.minis.beans.factory.xml;

import com.minis.beans.SimpleBeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.core.Resource;
import com.minis.beans.factory.BeanFactory;
import org.dom4j.Element;

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
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID,beanClassName);
            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
