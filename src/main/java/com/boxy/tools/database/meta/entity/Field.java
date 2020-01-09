package com.boxy.tools.database.meta.entity;

public class Field {
    private String fieldName;
    private String fieldType;
    private Integer length;
    private Integer scale;
    private Boolean nullable;
    private String defaultValue;
    private String remarks;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", length=" + length +
                ", scale=" + scale +
                ", nullable=" + nullable +
                ", defaultValue='" + defaultValue + '\'' +
                ", remarks='" + remarks + '\'' +
                "}\r\n";
    }
}
