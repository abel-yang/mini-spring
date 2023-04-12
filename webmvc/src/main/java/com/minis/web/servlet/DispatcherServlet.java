package com.minis.web.servlet;

import com.minis.beans.BeansException;
import com.minis.web.context.AnnotationConfigWebApplicationContext;
import com.minis.web.context.WebApplicationContext;
import com.minis.web.servlet.method.HandlerMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/4 16:16
 */
public class DispatcherServlet extends HttpServlet {
    private static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentApplicationContext;
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        this.webApplicationContext = new AnnotationConfigWebApplicationContext(contextConfigLocation, this.parentApplicationContext);
        refresh();
    }

    private void refresh() {
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapter(this.webApplicationContext);
        initViewResolver(this.webApplicationContext);
    }

    private void initViewResolver(WebApplicationContext webApplicationContext) {
        try {
            this.viewResolver = (ViewResolver) webApplicationContext.getBean("viewResolver");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private void initHandlerAdapter(WebApplicationContext wac) {
        try {
            this.handlerAdapter = (HandlerAdapter)wac.getBean("requestMappingHandlerAdapter");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private void initHandlerMappings(WebApplicationContext webApplicationContext) {
        this.handlerMapping = new RequestMappingHandlerMapping(webApplicationContext);
    }



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);

        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HandlerMethod handlerMethod = this.handlerMapping.getHandler(req);
        if(handlerMethod == null) {
            throw new IllegalArgumentException("未匹配到URL对应HandlerMethod");
        }
        HandlerAdapter ha = this.handlerAdapter;
        ModelAndView mv = ha.handle(req, resp, handlerMethod);
        render(req, resp, mv);
    }

    private void render(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        //获取model，写到request的Attribute中：
        Map<String, Object> modelMap = mv.getModel();
        View view = viewResolver.resolverViewName(mv.getViewName());
        view.render(modelMap, request, response);
    }

}
