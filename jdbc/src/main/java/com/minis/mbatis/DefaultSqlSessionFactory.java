package com.minis.mbatis;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import org.dom4j.DocumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/21 18:20
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Map<String, MapperNode> mapperNodeMap;
    private String mapperLocations;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void init() throws DocumentException {
        SqlXmlReader reader = new SqlXmlReader();
        reader.scanLocation(this.mapperLocations);
        mapperNodeMap = reader.getMapperNodeMap();
    }

    @Override
    public SqlSession openSession() {
        SqlSession sqlSession = new DefaultSqlSession();
        sqlSession.setJdbcTemplate(jdbcTemplate);
        sqlSession.setSqlSessionFactory(this);
        return sqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return mapperNodeMap.get(name);
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
