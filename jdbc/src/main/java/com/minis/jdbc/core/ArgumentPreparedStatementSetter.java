package com.minis.jdbc.core;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/14 10:56
 */
public class ArgumentPreparedStatementSetter {
    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }


    public void setValues(PreparedStatement stmt) throws SQLException {
        if(this.args == null) {
            return;
        }
        for(int i = 0; i < args.length; i++) {
            Object arg = args[i];
            doSetValue(stmt, i, arg);
        }
    }

    private void doSetValue(PreparedStatement stmt, int position, Object arg) throws SQLException {
        if(arg instanceof String) {
            stmt.setString(position + 1, (String) arg);
        }
        else if(arg instanceof Integer) {
            stmt.setInt(position + 1, (int) arg);
        }
        else if(arg instanceof Double) {
            stmt.setDouble(position + 1, (double)arg);
        }
        else if(arg instanceof Date) {
            stmt.setDate(position + 1, new java.sql.Date(((java.util.Date)arg).getTime()));
        }
    }
}
