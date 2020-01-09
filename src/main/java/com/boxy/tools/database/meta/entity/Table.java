package com.boxy.tools.database.meta.entity;

import java.util.ArrayList;
import java.util.List;

public class Table {
    // 表名
    private String tableName;
    // 类型
    private String tableType;
    // 备注
    private String remarks;
    // 字段
    private List<Field> fields = new ArrayList<Field>();
    // 主键
    private PrimaryKey primaryKey = new PrimaryKey();
    // 外键
    private List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    @Override
    public String toString() {
        return "\r\nTable{" +
                "tableName='" + tableName + '\'' +
                ", tableType='" + tableType + '\'' +
                ", remarks='" + remarks + '\'' +
                ", fields=" + fields +
                ", primaryKey=" + primaryKey +
                ", foreignKeys=" + foreignKeys +
                '}';
    }
}
