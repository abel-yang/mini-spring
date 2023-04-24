package com.minis.mbatis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/21 17:54
 */
public interface SqlSession {

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory factory);


    Object selectOne(String sqlId, Object[] args, PreparedStatementCallback callback);
}
