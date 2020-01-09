package com.boxy.tools.database.meta.browser;

import com.boxy.tools.database.meta.Browser;
import com.boxy.tools.database.meta.JdbcConfig;
import com.boxy.tools.database.meta.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultBrowser implements Browser {
    private Connection connection;

    public DefaultBrowser(JdbcConfig jdbcConfig) {
        this.connection = ConnectionFactory.getConnection(jdbcConfig.getDriverClass(), jdbcConfig.getUrl(), jdbcConfig.getUserName(), jdbcConfig.getPassword());
    }

    @Override
    public List<Map<String, Object>> findPage(int pageIndex, int pageSize, String tableName, List<String> fields) {
        List<Map<String, Object>> result = new ArrayList<>();

        String sql = selectBuilder(tableName, fields);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setMaxRows(pageIndex * pageSize);

            resultSet = statement.executeQuery();

            resultSet.absolute((pageIndex - 1) * pageSize);// + 1);

//            resultSet.previous();
            // resultSet

            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();

                for (String field : fields) {
                    map.put(field, resultSet.getObject(field));
                }

                result.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(connection, statement, resultSet);
        }
        return result;
    }

    @Override
    public long totalCount(String tableName) {
        long result = 0L;

        String sql = "select count(*) from " + tableName;;

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);

            resultSet = statement.executeQuery();

            if(resultSet.next()){
                result = resultSet.getLong(1);
            }

        } catch (Exception e) {

        } finally {
            ConnectionFactory.close(connection, statement, resultSet);
        }
        return result;
    }

    private String selectBuilder(String tableName, List<String> fields) {
        StringBuilder sb = new StringBuilder();

        sb.append("select ")
                .append(fields.stream().collect(Collectors.joining(",")))
                .append(" from ")
                .append(tableName);

        return sb.toString();
    }
}
