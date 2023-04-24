package com.minis.core;

import com.minis.beans.BeansException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/28 19:03
 */
public class ClassPathXmlResource implements Resource {
    Document document;
    Element rootElement;
    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String filename) throws BeansException {
        URL xmlPath = this.getClass().getClassLoader().getResource(filename);
        read(xmlPath);
    }

    public ClassPathXmlResource(URL xmlPath) throws BeansException {
        read(xmlPath);
    }

    private void read(URL path) throws BeansException {
        SAXReader saxReader = new SAXReader();
        try {
            this.document = saxReader.read(path);
            this.rootElement = document.getRootElement();
            this.elementIterator = this.rootElement.elementIterator();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new BeansException("读取配置信息出错: " + path);
        }
    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
