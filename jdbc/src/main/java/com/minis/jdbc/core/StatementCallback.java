package com.minis.jdbc.core;

import java.sql.Statement;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 17:23
 */
public interface StatementCallback {

    /**
     * 执行stmt
     *
     * @param statement
     * @return
     */
    Object doInStatement(Statement statement);
}
