package com.boxy.tools.database.meta;

public class JdbcConfig {
    private String type;
    private String driverClass;
    private String url;
    private String databaseName;
    private String userName;
    private String password;

    public JdbcConfig(String type, String driverClass, String url, String databaseName, String userName, String password) {
        this.type = type;
        this.driverClass = driverClass;
        this.url = url;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JdbcConfig{" +
                "type='" + type + '\'' +
                "driverClass='" + driverClass + '\'' +
                ", url='" + url + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
