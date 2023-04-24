package com.minis.mbatis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/21 18:29
 */
public class DefaultSqlSession implements SqlSession{
    private JdbcTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory factory) {
        this.sqlSessionFactory = factory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallback callback) {
        MapperNode mapperNode = sqlSessionFactory.getMapperNode(sqlId);
        return jdbcTemplate.query(mapperNode.getSql(), args, callback);
    }
}
