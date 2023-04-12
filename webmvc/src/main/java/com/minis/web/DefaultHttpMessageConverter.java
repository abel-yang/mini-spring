package com.minis.web;


import com.minis.beans.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/11 08:59
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter{
    String defaultContentType = "text/json;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";
    ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);
        writeInternal(obj, response);
        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        response.getWriter().write(objectMapper.writeValuesAsString(obj));
    }
}
