package com.boxy.tools.database.meta.explorer;

import com.boxy.tools.database.meta.JdbcConfig;
import com.boxy.tools.database.meta.util.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SqlServerExplorer extends DefaultExplorer {
    public SqlServerExplorer(JdbcConfig config) {
        this.connection = ConnectionFactory.getConnection(config.getDriverClass(), config.getUrl(), config.getUserName(), config.getPassword());
        this.databaseName = config.getDatabaseName();
        this.catalog = config.getDatabaseName();
        this.schema = "dbo";
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
            statement = connection.prepareStatement("SELECT " +
                "  a.name as TABLE_NAME, " +
                "  b.value as TABLE_COMMENT " +
                " FROM " +
                "  sysobjects a " +
                "  LEFT JOIN sys.extended_properties b ON a.id= b.major_id " +
                "  AND b.minor_id= 0 " +
                " where a.xtype in ('U', 'V') "); //AND a.name <> 'dtproperties' ");

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
            statement = connection.prepareStatement("SELECT A.name AS TABLE_NAME,  " +
                "       B.name AS COLUMN_NAME,  " +
                "       C.value AS COLUMN_COMMENT  " +
                "FROM (select object_id, name from sys.views " +
                "      union all " +
                "      select object_id, name from sys.tables) A  " +
                "    INNER JOIN sys.columns B  " +
                "        ON B.object_id = A.object_id  " +
                "    LEFT JOIN sys.extended_properties C  " +
                "        ON C.major_id = B.object_id  " +
                "           AND C.minor_id = B.column_id ");// +
                // " where A.name <> 'dtproperties' ");

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
