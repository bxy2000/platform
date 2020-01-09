package com.boxy.tools.database.meta.entity;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private String databaseName;
    private List<Table> tables = new ArrayList<Table>();

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "Database{" +
                "databaseName='" + databaseName + '\'' +
                ", tables=" + tables +
                '}';
    }
}
