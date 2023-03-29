package com.minis.test;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/28 18:49
 */
public class AServiceImpl implements AService{

    private String property1;
    private int level;


    public AServiceImpl(String property1, int level) {
        this.property1 = property1;
        this.level = level;
    }

    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    }


    public void setProperty1(String property1) { this.property1 = property1; }
}
