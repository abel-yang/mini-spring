package com.minis.web;

import com.minis.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:41
 */
public interface HandlerAdapter {

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
