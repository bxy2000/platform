package com.boxy.platform.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 数据连接
 */
@ApiModel(description = "数据连接")
@Entity
@Table(name = "database_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatabaseConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "connection_name")
    private String connectionName;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private String port;

    @Column(name = "database_name")
    private String databaseName;

    @Column(name = "params")
    private String params;

    @Column(name = "type")
    private String type;

    @Column(name = "driver")
    private String driver;

    @Column(name = "url")
    private String url;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "test")
    private Boolean test;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public DatabaseConnection connectionName(String connectionName) {
        this.connectionName = connectionName;
        return this;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getHost() {
        return host;
    }

    public DatabaseConnection host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public DatabaseConnection port(String port) {
        this.port = port;
        return this;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public DatabaseConnection databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getParams() {
        return params;
    }

    public DatabaseConnection params(String params) {
        this.params = params;
        return this;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public DatabaseConnection type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public DatabaseConnection driver(String driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public DatabaseConnection url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public DatabaseConnection username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public DatabaseConnection password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isTest() {
        return test;
    }

    public DatabaseConnection test(Boolean test) {
        this.test = test;
        return this;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatabaseConnection)) {
            return false;
        }
        return id != null && id.equals(((DatabaseConnection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DatabaseConnection{" +
            "id=" + getId() +
            ", connectionName='" + getConnectionName() + "'" +
            ", host='" + getHost() + "'" +
            ", port='" + getPort() + "'" +
            ", databaseName='" + getDatabaseName() + "'" +
            ", params='" + getParams() + "'" +
            ", type='" + getType() + "'" +
            ", driver='" + getDriver() + "'" +
            ", url='" + getUrl() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", test='" + isTest() + "'" +
            "}";
    }
}
