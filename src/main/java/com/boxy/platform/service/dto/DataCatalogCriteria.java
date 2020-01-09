package com.boxy.platform.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.boxy.platform.domain.DataCatalog} entity. This class is used
 * in {@link com.boxy.platform.web.rest.DataCatalogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /data-catalogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataCatalogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter type;

    private StringFilter icon;

    private StringFilter tableName;

    private StringFilter tableType;

    private StringFilter remarks;

    private LongFilter childrenId;

    private LongFilter dbConnectionId;

    private LongFilter parentId;

    public DataCatalogCriteria(){
    }

    public DataCatalogCriteria(DataCatalogCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.tableName = other.tableName == null ? null : other.tableName.copy();
        this.tableType = other.tableType == null ? null : other.tableType.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.childrenId = other.childrenId == null ? null : other.childrenId.copy();
        this.dbConnectionId = other.dbConnectionId == null ? null : other.dbConnectionId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public DataCatalogCriteria copy() {
        return new DataCatalogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getIcon() {
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public StringFilter getTableName() {
        return tableName;
    }

    public void setTableName(StringFilter tableName) {
        this.tableName = tableName;
    }

    public StringFilter getTableType() {
        return tableType;
    }

    public void setTableType(StringFilter tableType) {
        this.tableType = tableType;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(LongFilter childrenId) {
        this.childrenId = childrenId;
    }

    public LongFilter getDbConnectionId() {
        return dbConnectionId;
    }

    public void setDbConnectionId(LongFilter dbConnectionId) {
        this.dbConnectionId = dbConnectionId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataCatalogCriteria that = (DataCatalogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(type, that.type) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(tableName, that.tableName) &&
            Objects.equals(tableType, that.tableType) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(childrenId, that.childrenId) &&
            Objects.equals(dbConnectionId, that.dbConnectionId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        type,
        icon,
        tableName,
        tableType,
        remarks,
        childrenId,
        dbConnectionId,
        parentId
        );
    }

    @Override
    public String toString() {
        return "DataCatalogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (icon != null ? "icon=" + icon + ", " : "") +
                (tableName != null ? "tableName=" + tableName + ", " : "") +
                (tableType != null ? "tableType=" + tableType + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (childrenId != null ? "childrenId=" + childrenId + ", " : "") +
                (dbConnectionId != null ? "dbConnectionId=" + dbConnectionId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
