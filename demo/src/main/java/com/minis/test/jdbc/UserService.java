//package com.minis.test.jdbc;
//
//import com.minis.beans.factory.annotation.Autowired;
//import com.minis.jdbc.core.JdbcTemplate;
//import com.minis.mbatis.SqlSession;
//import com.minis.mbatis.SqlSessionFactory;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @author abel
// * @version 1.0
// * @date 2023/4/14 09:08
// */
//public class UserService {
//    @Autowired
//    SqlSessionFactory sqlSessionFactory;
//
//
//    public User findUser(String tel) {
//        String sqlId = "com.minis.test.jdbc.User.getUserInfo";
//        return (User) sqlSessionFactory.openSession().selectOne(sqlId, new Object[]{1}, stmt -> {
//            try {
//                ResultSet rs = stmt.executeQuery();
//                User user = new User();
//                user.setId(rs.getLong("id"));
//                user.setName(rs.getString("name"));
//                return user;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
//    }
//
//    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//        this.sqlSessionFactory = sqlSessionFactory;
//    }
//}
