package com.minis.jdbc.datasource.pool;

import com.minis.beans.util.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/14 13:38
 */
public class PooledDataSource implements DataSource {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Properties connectionProperties;
    private BlockingQueue<PooledConnection> activeConnections;
    private BlockingQueue<PooledConnection> inActiveConnections;
    private int maxSize = 10;
    private int initialSize = 2;



    public void init() throws SQLException {
        this.inActiveConnections = new ArrayBlockingQueue<>(initialSize);
        this.activeConnections = new ArrayBlockingQueue<>(maxSize);
        for(int i = 0; i < initialSize; i ++) {
            Connection connection = this.getConnection(getUsername(), getPassword());
            PooledConnection pooledConnection = new PooledConnection(connection, false, this);
            inActiveConnections.add(pooledConnection);
        }
    }



    @Override
    public Connection getConnection() throws SQLException {
        return this.getAvailableConnection();
    }

    private PooledConnection getAvailableConnection() {
        PooledConnection pooledConnection = null;
        try {
            pooledConnection = this.inActiveConnections.take();
            pooledConnection.setActive(true);
            this.activeConnections.offer(pooledConnection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pooledConnection;
    }

    public void release(PooledConnection connection) throws SQLException {
        try {
            if(connection != null && connection.isActive()) {
                connection.setActive(false);
                this.activeConnections.remove(connection);
                this.inActiveConnections.add(connection);
            }
        } catch (Exception e) {
            throw new SQLException("release connection: " + e.getMessage());
        }
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
        return this.getConnectionForDriverManager(this.getUrl(), mergedProps);
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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }
}
