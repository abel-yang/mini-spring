package com.minis.jdbc.datasource;

import com.minis.beans.util.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/14 09:32
 */
public class SingleConnectionDataSource implements DataSource {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Properties connectionProperties;
    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {
        return this.getConnection(getUsername(), getPassword());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.getConnectionForDriver(username, password);
    }

    protected Connection getConnectionForDriver(String username, String password) throws SQLException {
        Properties mergedProps = new Properties();
        Properties connProps = getConnectionProperties();
        if(connProps != null) {
            mergedProps.putAll(connProps);
        }
        if(StringUtils.hasText(username)) {
            mergedProps.put("user", username);
        }
        if(StringUtils.hasText(password)) {
            mergedProps.put("password", password);
        }
        this.connection = this.getConnectionForDriverManager(this.getUrl(), mergedProps);
        return this.connection;
    }

    private Connection getConnectionForDriverManager(String url, Properties mergedProps) throws SQLException {
        return DriverManager.getConnection(url, mergedProps);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        try {
            Class.forName(this.driverClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("not found JDBC Driver class[" + driverClassName + "]");
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
