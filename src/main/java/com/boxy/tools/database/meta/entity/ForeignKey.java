package com.boxy.tools.database.meta.entity;

import java.util.ArrayList;
import java.util.List;

public class ForeignKey {
    private String foreignKeyName;
    private List<String> foreignKeys = new ArrayList<String>();
    private String referencesTableName;
    private List<String> referencesTablePrimaryKeys = new ArrayList<String>();

    public String getForeignKeyName() {
        return foreignKeyName;
    }

    public void setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
    }

    public List<String> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<String> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public String getReferencesTableName() {
        return referencesTableName;
    }

    public void setReferencesTableName(String referencesTableName) {
        this.referencesTableName = referencesTableName;
    }

    public List<String> getReferencesTablePrimaryKeys() {
        return referencesTablePrimaryKeys;
    }

    public void setReferencesTablePrimaryKeys(List<String> referencesTablePrimaryKeys) {
        this.referencesTablePrimaryKeys = referencesTablePrimaryKeys;
    }

    @Override
    public String toString() {
        return "ForeignKey{" +
                "foreignKeyName='" + foreignKeyName + '\'' +
                ", foreignKeys=" + foreignKeys +
                ", referencesTableName='" + referencesTableName + '\'' +
                ", referencesTablePrimaryKeys=" + referencesTablePrimaryKeys +
                '}';
    }
}
