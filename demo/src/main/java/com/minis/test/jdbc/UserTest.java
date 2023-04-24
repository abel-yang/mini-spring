package com.minis.test.jdbc;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/14 09:21
 */
public class UserTest {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
        ctx.refresh();
        UserService service = (UserService)ctx.getBean("userService");
        User user = service.findUser("18866668888");
        System.out.println("查询用户数据：" + user.getId() +", name: " + user.getName());
    }
}
