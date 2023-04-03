package com.minis.test;

import com.minis.beans.factory.annotation.Autowired;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/31 16:00
 */
public class BaseService {
    @Autowired
    private BaseBaseService bbs;

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void sayHello() {
        System.out.println("Base Service says Hello");
        bbs.sayHello();
    }
}
