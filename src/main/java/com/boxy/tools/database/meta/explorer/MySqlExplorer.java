package com.boxy.tools.database.meta.explorer;

import com.boxy.tools.database.meta.JdbcConfig;
import com.boxy.tools.database.meta.util.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MySqlExplorer extends DefaultExplorer {
    public MySqlExplorer(JdbcConfig config) {
        this.connection = ConnectionFactory.getConnection(config.getDriverClass(), config.getUrl(), config.getUserName(), config.getPassword());
        this.databaseName = config.getDatabaseName();
        this.catalog = config.getDatabaseName();
        this.schema = null;
        try {
            this.databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Map<String, String> getTableNameAndRemarks() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, String> result = new HashMap<>();
        try {
            statement = connection.prepareStatement("SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='" + this.databaseName + "'");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.put(resultSet.getString("TABLE_NAME"), resultSet.getString("TABLE_COMMENT"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(statement, resultSet);
        }

        return result;
    }

    @Override
    protected Map<String, Map<String, String>> getFieldNameAndRemarks() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, Map<String, String>> result = new HashMap<>();
        try {
            statement = connection.prepareStatement("SELECT t.TABLE_NAME,c.COLUMN_NAME,c.COLUMN_COMMENT FROM information_schema.TABLES t,INFORMATION_SCHEMA.Columns c WHERE c.TABLE_NAME=t.TABLE_NAME AND t.`TABLE_SCHEMA`='" + this.databaseName + "'");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                String columnComment = resultSet.getString("COLUMN_COMMENT");

                Map<String, String> field = new HashMap<>();
                if (!result.containsKey(tableName)) {
                    result.put(tableName, field);
                } else {
                    field = result.get(tableName);
                }

                field.put(columnName, columnComment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close(statement, resultSet);
        }

        return result;
    }

    private void close(Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
