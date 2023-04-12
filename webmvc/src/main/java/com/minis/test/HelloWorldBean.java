package com.minis.test;

import com.minis.beans.stereotype.Controller;
import com.minis.web.bind.annotation.RequestMapping;
import com.minis.web.bind.annotation.ResponseBody;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/4 16:46
 */
@Controller
public class HelloWorldBean {
    @ResponseBody
    @RequestMapping("/test")
    public NameParam doGet(NameParam param) {
        return param;
    }
    public String doPost() {
        return "hello world!";
    }
}
