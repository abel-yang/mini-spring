package com.minis.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:41
 */
public interface HandlerAdapter {

    void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
