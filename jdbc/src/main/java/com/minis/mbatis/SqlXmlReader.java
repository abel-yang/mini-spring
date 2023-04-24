package com.minis.mbatis;

import com.minis.beans.util.Assert;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/21 17:58
 */
public class SqlXmlReader {
    private Map<String, MapperNode> mapperNodeMap = new HashMap<>();


    private Map<String, MapperNode> buildMapperNode(String filePath) throws DocumentException {
        SAXReader reader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        Document document = reader.read(xmlPath);
        Element root = document.getRootElement();
        String namespace = root.attributeValue("namespace");
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sql = element.getText();
            MapperNode node = new MapperNode();
            node.setId(id);
            node.setParameterType(parameterType);
            node.setResultType(resultType);
            node.setSql(sql);
            this.mapperNodeMap.put(namespace + "." + id, node);
        }
        return this.mapperNodeMap;
    }

    public void scanLocation(String location) throws DocumentException {
        String locationPath = this.getClass().getClassLoader().getResource("").getPath() + location;
        File dir = new File(locationPath);
        Assert.notNull(dir, "【" + location + "】location path not found resource");
        for(File file: dir.listFiles()) {
            if(file.isDirectory()) {
                scanLocation(location + "/" + file.getName());
            }else {
                buildMapperNode(location + "/" + file.getName());
            }
        }
    }

    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }
}
