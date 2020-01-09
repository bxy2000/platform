package com.boxy.platform.repository;

import com.boxy.platform.domain.DataFields;
import com.boxy.platform.domain.DatabaseConnection;
import com.boxy.platform.service.util.StringUtil;
import com.boxy.tools.database.meta.*;
import com.boxy.tools.database.meta.entity.Database;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DbToolsRepository {
    public Database getDatabase(String databaseType, String driverClass, String url, String databaseName, String username, String password) {
        JdbcConfig jdbcConfig = new JdbcConfig(databaseType, driverClass, url, databaseName, username, password);

        Explorer explorer = ExplorerFactory.createExplorer(jdbcConfig);

        return explorer.getDatabase();
    }

    public List<Map<String, Object>> findTable(DatabaseConnection databaseConnection, String tableName, List<DataFields> fields, Pageable pageable) {
        String url = databaseConnection.getUrl();
        if (!StringUtil.isEmpty(databaseConnection.getParams())) {
            url += "?" + databaseConnection.getParams();
        }

        JdbcConfig jdbcConfig = new JdbcConfig(databaseConnection.getType(), databaseConnection.getDriver(), url, databaseConnection.getDatabaseName(), databaseConnection.getUsername(), databaseConnection.getPassword());


        Browser browser = BrowserFactory.createExplorer(jdbcConfig);
        List<String> field = fields.stream().map(u->u.getFieldName()).collect(Collectors.toList());

        return browser.findPage(pageable.getPageNumber()+1, pageable.getPageSize(), tableName, field);
    }

    public long findTotal(DatabaseConnection databaseConnection, String tableName) {
        String url = databaseConnection.getUrl();
        if (!StringUtil.isEmpty(databaseConnection.getParams())) {
            url += "?" + databaseConnection.getParams();
        }

        JdbcConfig jdbcConfig = new JdbcConfig(databaseConnection.getType(), databaseConnection.getDriver(), url, databaseConnection.getDatabaseName(), databaseConnection.getUsername(), databaseConnection.getPassword());

        Browser browser = BrowserFactory.createExplorer(jdbcConfig);

        return browser.totalCount(tableName);
    }


    private String buildSelect(String tableName, List<DataFields> fields) {
        StringBuilder sb = new StringBuilder();

        sb.append("select ")
            .append(fields.stream().map(u -> u.getFieldName()).collect(Collectors.joining(",")))
            .append(" from ")
            .append(tableName);
        // System.out.println(sb.toString());
        return sb.toString();
    }
}
