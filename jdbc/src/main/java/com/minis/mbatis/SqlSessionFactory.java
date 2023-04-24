package com.minis.mbatis;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/21 17:54
 */
public interface SqlSessionFactory {

    SqlSession openSession();

    MapperNode getMapperNode(String name);
}
