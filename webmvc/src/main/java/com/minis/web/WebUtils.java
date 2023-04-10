package com.minis.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/9 09:44
 */
public class WebUtils {


    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if(prefix == null) {
            prefix = "";
        }
        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if(prefix.isEmpty() || paramName.startsWith(prefix)) {
                String unPrefixed = paramName.substring(prefix.length());
                String value = request.getParameter(unPrefixed);

                params.put(unPrefixed, value);
            }
        }
        return params;
    }
}
