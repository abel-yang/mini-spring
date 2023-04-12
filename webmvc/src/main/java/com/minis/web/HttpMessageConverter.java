package com.minis.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/11 08:58
 */
public interface HttpMessageConverter {


    void write(Object obj, HttpServletResponse response) throws IOException;
}
