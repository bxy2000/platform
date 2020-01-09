package com.boxy.tools.database.meta.entity;

import java.util.ArrayList;
import java.util.List;

public class PrimaryKey {
    // 主键名称
    private String primaryKeyName;
    // 主键列
    private List<String> primaryKeys = new ArrayList<String>();

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    @Override
    public String toString() {
        return "PrimaryKey{" +
                "primaryKeyName='" + primaryKeyName + '\'' +
                ", primaryKeys=" + primaryKeys +
                '}';
    }
}
