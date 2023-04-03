package com.minis.context;

import java.util.EventListener;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 16:52
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);
}
