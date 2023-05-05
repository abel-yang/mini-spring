package com.minis.test.aop;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/26 20:56
 */
public class ActionTest {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
        ctx.refresh();
        Action action = (Action) ctx.getBean("action");
        action.print();

    }
}
