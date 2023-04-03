package com.minis.core.env;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/1 15:57
 */
public interface Environment extends PropertyResolver{
    /**
     * 获取激活的 profile
     *
     * @return
     */
    String[] getActiveProfiles();

    /**
     * 获取默认profile
     *
     * @return
     */
    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);
}
