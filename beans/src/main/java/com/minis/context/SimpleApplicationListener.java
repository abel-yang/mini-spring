package com.minis.context;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/8 16:09
 */
public class SimpleApplicationListener implements ApplicationListener<ContextRefreshEvent>{

    @Override
    public void onApplicationEvent(ContextRefreshEvent event) {
        System.out.println(event);
    }
}
