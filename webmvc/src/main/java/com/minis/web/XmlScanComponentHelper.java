package com.minis.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 11:32
 */
public class XmlScanComponentHelper {

    public static List<String> getNodeValue(URL xmlPath) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        List<String> packages = new ArrayList<>();
        Element root = document.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element element = it.next();
            packages.add(element.attributeValue("base-package"));
        }
        return packages;
    }
}
