package com.minis.jdbc.core;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 17:08
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public Object query(StatementCallback stmtCallback) {
        String url = "jdbc:postgresql://10.10.104.52:54321/workbench?currentSchema=public&allowMultiQueries=true";
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();
            return stmtCallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {

            }
        }

        return null;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback callback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getDataSource().getConnection();
            stmt = conn.prepareStatement(sql);
            ArgumentPreparedStatementSetter setter = new ArgumentPreparedStatementSetter(args);
            setter.setValues(stmt);
            return callback.doInPreparedStatement(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }


    public <T> T query(String sql, Object[] args, RowMapper<T> rowMapper) {
        List<T> retList = queryList(sql, args, rowMapper);
        if(retList == null || retList.size() == 0) {
            return null;
        }
        return retList.get(0);
    }


    public <T> List<T> queryList(String sql, Object[] args, RowMapper<T> rowMapper) {
        Connection conn = null;
        PreparedStatement stmt = null;
        RowMapperResultSetExtractor<T> extractor = new RowMapperResultSetExtractor<>(rowMapper);
        try {
            conn = getDataSource().getConnection();
            stmt = conn.prepareStatement(sql);
            ArgumentPreparedStatementSetter setter = new ArgumentPreparedStatementSetter(args);
            setter.setValues(stmt);
            ResultSet rs = stmt.executeQuery();
            return extractor.extractData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null) {
                    stmt.close();
                }
                if(conn != null) {
                   conn.close();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
