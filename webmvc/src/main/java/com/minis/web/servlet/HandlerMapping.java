package com.minis.web.servlet;

import com.minis.web.servlet.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:39
 */
public interface HandlerMapping {

    /**
     * 根据url匹配处理方法实例
     *
     * @param request
     * @return
     */
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
