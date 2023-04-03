package com.minis.context;

import java.util.EventObject;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:15
 */
public class ApplicationEvent extends EventObject {
    protected String msg;

    public ApplicationEvent(Object source) {
        super(source);
        this.msg = source.toString();
    }
}
