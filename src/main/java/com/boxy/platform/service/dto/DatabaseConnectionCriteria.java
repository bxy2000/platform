package com.boxy.platform.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.boxy.platform.domain.DatabaseConnection} entity. This class is used
 * in {@link com.boxy.platform.web.rest.DatabaseConnectionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /database-connections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DatabaseConnectionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter connectionName;

    private StringFilter host;

    private StringFilter port;

    private StringFilter databaseName;

    private StringFilter params;

    private StringFilter type;

    private StringFilter driver;

    private StringFilter url;

    private StringFilter username;

    private StringFilter password;

    private BooleanFilter test;

    public DatabaseConnectionCriteria(){
    }

    public DatabaseConnectionCriteria(DatabaseConnectionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.connectionName = other.connectionName == null ? null : other.connectionName.copy();
        this.host = other.host == null ? null : other.host.copy();
        this.port = other.port == null ? null : other.port.copy();
        this.databaseName = other.databaseName == null ? null : other.databaseName.copy();
        this.params = other.params == null ? null : other.params.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.driver = other.driver == null ? null : other.driver.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.test = other.test == null ? null : other.test.copy();
    }

    @Override
    public DatabaseConnectionCriteria copy() {
        return new DatabaseConnectionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(StringFilter connectionName) {
        this.connectionName = connectionName;
    }

    public StringFilter getHost() {
        return host;
    }

    public void setHost(StringFilter host) {
        this.host = host;
    }

    public StringFilter getPort() {
        return port;
    }

    public void setPort(StringFilter port) {
        this.port = port;
    }

    public StringFilter getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(StringFilter databaseName) {
        this.databaseName = databaseName;
    }

    public StringFilter getParams() {
        return params;
    }

    public void setParams(StringFilter params) {
        this.params = params;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getDriver() {
        return driver;
    }

    public void setDriver(StringFilter driver) {
        this.driver = driver;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public BooleanFilter getTest() {
        return test;
    }

    public void setTest(BooleanFilter test) {
        this.test = test;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DatabaseConnectionCriteria that = (DatabaseConnectionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(connectionName, that.connectionName) &&
            Objects.equals(host, that.host) &&
            Objects.equals(port, that.port) &&
            Objects.equals(databaseName, that.databaseName) &&
            Objects.equals(params, that.params) &&
            Objects.equals(type, that.type) &&
            Objects.equals(driver, that.driver) &&
            Objects.equals(url, that.url) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(test, that.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        connectionName,
        host,
        port,
        databaseName,
        params,
        type,
        driver,
        url,
        username,
        password,
        test
        );
    }

    @Override
    public String toString() {
        return "DatabaseConnectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (connectionName != null ? "connectionName=" + connectionName + ", " : "") +
                (host != null ? "host=" + host + ", " : "") +
                (port != null ? "port=" + port + ", " : "") +
                (databaseName != null ? "databaseName=" + databaseName + ", " : "") +
                (params != null ? "params=" + params + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (driver != null ? "driver=" + driver + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (test != null ? "test=" + test + ", " : "") +
            "}";
    }

}
