package com.minis.test;

import com.minis.beans.stereotype.Controller;
import com.minis.web.RequestMapping;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/4 16:46
 */
@Controller
public class HelloWorldBean {
    @RequestMapping("/test")
    public String doGet() {
        return "hello world!";
    }
    public String doPost() {
        return "hello world!";
    }
}
