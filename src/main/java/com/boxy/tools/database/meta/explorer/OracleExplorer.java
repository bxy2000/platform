package com.boxy.tools.database.meta.explorer;

import com.boxy.tools.database.meta.JdbcConfig;
import com.boxy.tools.database.meta.util.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class OracleExplorer extends DefaultExplorer {

    public OracleExplorer(JdbcConfig config) {
        this.connection = ConnectionFactory.getConnection(config.getDriverClass(), config.getUrl(), config.getUserName(), config.getPassword());
        this.catalog = null;
        this.schema = config.getUserName();
//        this.catalog = null;
//        this.schema = config.getUserName();
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
            statement = connection.prepareStatement(
                "SELECT A.TABLE_NAME AS TABLE_NAME, B.COMMENTS AS TABLE_COMMENT " +
                "FROM USER_TABLES A " +
                "LEFT OUTER JOIN USER_TAB_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME " +
                "UNION " +
                "SELECT A.VIEW_NAME, B.COMMENTS " +
                "FROM USER_VIEWS A " +
                "LEFT OUTER JOIN USER_TAB_COMMENTS B ON A.VIEW_NAME = B.TABLE_NAME");

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
            statement = connection.prepareStatement(
                "SELECT A.TABLE_NAME, A.COLUMN_NAME, B.COMMENTS AS COLUMN_COMMENT " +
                "FROM USER_TAB_COLS A " +
                "LEFT OUTER JOIN USER_COL_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME " +
                "ORDER BY A.TABLE_NAME ");

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
