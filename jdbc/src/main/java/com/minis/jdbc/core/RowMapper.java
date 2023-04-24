package com.minis.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/14 11:17
 */
public interface RowMapper<T> {

    /**
     * 将一行记录映射为对象
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws
     */
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
