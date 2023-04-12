package com.minis.web.context;

import com.minis.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 14:29
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * 获取servlet上下文
     *
     * @return
     */
    ServletContext getServletContext();

    /**
     * 设置servlet上下文
     *
     * @param servletContext
     */
    void setServletContext(ServletContext servletContext);
}
