package com.minis.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/14 11:18
 */
public interface ResultSetExtractor<T> {
    /**
     * 将数据处理为集合
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    T extractData(ResultSet rs) throws SQLException;
}
