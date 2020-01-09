package com.boxy.tools.database.meta.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ConnectionFactory {
    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    public static Map<String, HikariDataSource> pools = new HashMap<>();

    public static Map<String, String> drivers = new HashMap<>();

    private static void loadDriver(String driver) {
        if(drivers.get(driver) == null) {
            try {
                Class.forName(driver);
            } catch (Exception e){
                logger.error("加载数据库驱动失败！", e);
            }
            drivers.put(driver, driver);
        }
    }

    public static Connection getConnection(String driver, String url, String userName, String password){
        Connection result = null;
        try {
            loadDriver(driver);
            result = DriverManager.getConnection(url, userName, password);
        }catch (Exception e){
            logger.error("获取数据库连接失败！", e);
        }

        return result;
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            logger.error("关闭数据库资源失败！", e);
        }
    }

    public static Connection getConnection2(String url, String userName, String password){
        Connection result = null;
        try {
            HikariDataSource dataSource = pools.get(url);
            if (dataSource == null) {
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(url);
                config.setUsername(userName);
                config.setPassword(password);
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                dataSource = new HikariDataSource(config);

                pools.put(url, dataSource);
            }

            result = dataSource.getConnection();
        }catch (Exception e){
            logger.error("获取数据库连接失败！", e);
        }

        return result;
    }

    public static void close2(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            logger.error("关闭数据库资源失败！", e);
        }
    }
}
