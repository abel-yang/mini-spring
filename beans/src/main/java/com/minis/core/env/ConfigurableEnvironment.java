package com.minis.core.env;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 13:37
 */
public interface ConfigurableEnvironment extends Environment {

    void addPropertySource(PropertySource propertySource);

    void removePropertySource(PropertySource propertySource);

    void setActiveProfiles(String... profiles);

    void addActiveProfile(String profile);

    void setDefaultProfiles(String... profiles);

}
