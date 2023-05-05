package com.minis.aop;

/**
 * @author abel
 * @version 1.0
 * @date 2023/5/5 18:57
 */
public interface Pointcut {

    MethodMatcher getMethodMatcher();
}
