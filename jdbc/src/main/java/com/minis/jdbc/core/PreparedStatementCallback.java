package com.minis.jdbc.core;

import java.sql.PreparedStatement;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 17:22
 */
public interface PreparedStatementCallback {

    /**
     * 回调
     *
     * @param preparedStatement
     * @return
     */
    Object doInPreparedStatement(PreparedStatement preparedStatement);
}
