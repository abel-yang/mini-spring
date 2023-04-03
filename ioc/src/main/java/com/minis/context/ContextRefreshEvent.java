package com.minis.context;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 16:53
 */
public class ContextRefreshEvent extends ApplicationEvent{


    public ContextRefreshEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
