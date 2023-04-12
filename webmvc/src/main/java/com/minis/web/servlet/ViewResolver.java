package com.minis.web.servlet;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 13:12
 */
public interface ViewResolver {
    /**
     * 解析视图
     *
     * @param viewName
     * @return
     * @throws Exception
     */
    View resolverViewName(String viewName) throws Exception;
}
