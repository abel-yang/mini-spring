package com.minis.web.bind.support;

import com.minis.web.WebDataBinder;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/10 09:25
 */
public interface WebBindingInitializer {

    void initBinder(WebDataBinder binder);
}
