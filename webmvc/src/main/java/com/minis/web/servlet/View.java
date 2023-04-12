package com.minis.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 11:23
 */
public interface View {

    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    default String getContentType() { return null; }

    void setContentType(String contentType);

    void setUrl(String url);

    String getUrl();

    void setRequestContextAttribute(String requestContextAttribute);

    String getRequestContextAttribute();
}
