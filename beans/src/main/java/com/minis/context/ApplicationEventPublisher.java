package com.minis.context;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:14
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     *
     * @param event
     */
    void publishEvent(ApplicationEvent event);

    /**
     * 增加事件监听者
     *
     * @param listener
     */
    void addApplicationListener(ApplicationListener<? extends ApplicationEvent> listener);
}
