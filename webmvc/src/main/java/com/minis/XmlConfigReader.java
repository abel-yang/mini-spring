package com.minis;

import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/3 13:05
 */
public class XmlConfigReader {

    public Map<String, MappingValue> loaderConfig(Resource resource) {
        Map<String, MappingValue> mappings = new HashMap<>();
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String uri = element.attributeValue("id");
            String clz = element.attributeValue("class");
            String method = element.attributeValue("value");

            mappings.put(uri, new MappingValue(uri, clz, method));
        }

        return mappings;
    }
}
