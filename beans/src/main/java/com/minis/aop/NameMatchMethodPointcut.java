package com.minis.aop;

import com.minis.beans.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 19:01
 */
public class NameMatchMethodPointcut implements Pointcut, MethodMatcher{

    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if(method.getName().equals(mappedName) || isMatch(method.getName(), mappedName)) {
            return true;
        }
        return false;
    }

    private boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
