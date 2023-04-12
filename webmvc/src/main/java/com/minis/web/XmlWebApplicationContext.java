package com.minis.web;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:28
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String filename) {
        super(filename);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
