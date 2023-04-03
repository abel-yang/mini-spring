package com.minis.test;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/31 16:01
 */
public class BaseBaseService {

    private AServiceImpl as;
    private CService cc;

    public BaseBaseService(CService cc) {
        this.cc = cc;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public void sayHello() {
        System.out.println("bbs ......");
    }
}
