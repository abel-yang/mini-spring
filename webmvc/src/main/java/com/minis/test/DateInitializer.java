package com.minis.test;

import com.minis.web.bind.support.WebBindingInitializer;
import com.minis.web.WebDataBinder;

import java.util.Date;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/10 09:25
 */
public class DateInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false));
    }
}
