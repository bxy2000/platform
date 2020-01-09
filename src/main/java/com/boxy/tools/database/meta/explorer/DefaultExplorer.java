package com.boxy.tools.database.meta.explorer;

import com.boxy.tools.database.meta.Explorer;
import com.boxy.tools.database.meta.entity.*;
import com.boxy.tools.database.meta.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DefaultExplorer implements Explorer {
    protected Connection connection;
    protected String databaseName;
    protected String catalog;
    protected String schema;
    protected DatabaseMetaData databaseMetaData;

    private Map<String, String> tables;
    private Map<String, Map<String, String>> fields;

    @Override
    public Database getDatabase() {
        Database result = new Database();

        try {
            result.setDatabaseName(databaseName);
            result.setTables(this.getTables());
        }catch (Exception e) {

        } finally {
            ConnectionFactory.close(connection, null, null);
        }
        return result;
    }

    protected abstract Map<String, String> getTableNameAndRemarks();

    protected abstract Map<String, Map<String, String>> getFieldNameAndRemarks();

    private List<Table> getTables() {
        List<Table> result = new ArrayList<>();
        try {
            ResultSet resultSet = this.databaseMetaData.getTables(catalog, schema, "%", new String[]{"TABLE", "VIEW"});

            this.tables = this.getTableNameAndRemarks();
            this.fields = this.getFieldNameAndRemarks();

            while (resultSet.next()) {
                Table table = new Table();

                table.setTableName(resultSet.getString("TABLE_NAME"));
                table.setTableType(resultSet.getString("TABLE_TYPE"));

                // table.setRemarks(resultSet.getString("REMARKS"));

                table.setRemarks(this.tables.get(table.getTableName()));

                table.setFields(this.getFields(table.getTableName()));
                table.setPrimaryKey(this.getPrimaryKey(table.getTableName()));
                table.setForeignKeys(this.getForeignKeys(table.getTableName()));

                result.add(table);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Field> getFields(String tableName) {
        List<Field> result = new ArrayList<>();
        try {
            ResultSet resultSet = this.databaseMetaData.getColumns(catalog, schema, tableName, null);

            while (resultSet.next()) {
                Field field = new Field();

                field.setFieldName(resultSet.getString("COLUMN_NAME"));
                field.setFieldType(resultSet.getString("TYPE_NAME"));
                field.setLength(resultSet.getInt("COLUMN_SIZE"));
                field.setScale(resultSet.getInt("DECIMAL_DIGITS"));
                field.setNullable(resultSet.getBoolean("NULLABLE"));
                field.setDefaultValue(resultSet.getString("COLUMN_DEF"));

                // field.setRemarks(resultSet.getString("REMARKS"));
                String remarks = "";
                Map<String, String> map = this.fields.get(tableName);
                if(map != null) {
                    remarks = map.get(field.getFieldName());
                }
                field.setRemarks(remarks);
                //field.setRemarks(this.fields.get(tableName).get(field.getFieldName()));
                result.add(field);
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private PrimaryKey getPrimaryKey(String tableName) {
        PrimaryKey result = new PrimaryKey();

        try {
            ResultSet resultSet = this.databaseMetaData.getPrimaryKeys(catalog, schema, tableName);
            while (resultSet.next()) {
                String primaryKeyName = resultSet.getString("PK_NAME");
                String primaryColumnName = resultSet.getString("COLUMN_NAME");

                result.setPrimaryKeyName(primaryKeyName);
                result.getPrimaryKeys().add(primaryColumnName);
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<ForeignKey> getForeignKeys(String tableName) {
        List<ForeignKey> result = new ArrayList<>();

        try {
            ResultSet resultSet = this.databaseMetaData.getImportedKeys(catalog, schema, tableName);
            while (resultSet.next()) {
                String foreignKeyName = resultSet.getString("FK_NAME");
                String foreignKeyColumnName = resultSet.getString("FKCOLUMN_NAME");
                String primaryKeyTableName = resultSet.getString("PKTABLE_NAME");
                String primaryKeyColumnName = resultSet.getString("PKCOLUMN_NAME");

                ForeignKey foreignKey = getForeignKey(foreignKeyName, result);

                foreignKey.setForeignKeyName(foreignKeyName);
                foreignKey.getForeignKeys().add(foreignKeyColumnName);
                foreignKey.setReferencesTableName(primaryKeyTableName);
                foreignKey.getReferencesTablePrimaryKeys().add(primaryKeyColumnName);
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private ForeignKey getForeignKey(String foreignKeyName, List<ForeignKey> list) {
        for (ForeignKey item : list) {
            if (foreignKeyName.equals(item.getForeignKeyName())) {
                return item;
            }
        }
        ForeignKey result = new ForeignKey();
        list.add(result);

        return result;
    }
}
