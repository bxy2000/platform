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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.boxy.platform.domain.DataFields} entity. This class is used
 * in {@link com.boxy.platform.web.rest.DataFieldsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /data-fields?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataFieldsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fieldName;

    private StringFilter fieldType;

    private IntegerFilter length;

    private IntegerFilter scale;

    private BooleanFilter allowNull;

    private BooleanFilter primaryKey;

    private StringFilter remarks;

    private BooleanFilter stop;

    private InstantFilter createDate;

    private InstantFilter updateDate;

    private InstantFilter modifyDate;

    private LongFilter dataCatalogId;

    public DataFieldsCriteria(){
    }

    public DataFieldsCriteria(DataFieldsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fieldName = other.fieldName == null ? null : other.fieldName.copy();
        this.fieldType = other.fieldType == null ? null : other.fieldType.copy();
        this.length = other.length == null ? null : other.length.copy();
        this.scale = other.scale == null ? null : other.scale.copy();
        this.allowNull = other.allowNull == null ? null : other.allowNull.copy();
        this.primaryKey = other.primaryKey == null ? null : other.primaryKey.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.stop = other.stop == null ? null : other.stop.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.modifyDate = other.modifyDate == null ? null : other.modifyDate.copy();
        this.dataCatalogId = other.dataCatalogId == null ? null : other.dataCatalogId.copy();
    }

    @Override
    public DataFieldsCriteria copy() {
        return new DataFieldsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    public StringFilter getFieldType() {
        return fieldType;
    }

    public void setFieldType(StringFilter fieldType) {
        this.fieldType = fieldType;
    }

    public IntegerFilter getLength() {
        return length;
    }

    public void setLength(IntegerFilter length) {
        this.length = length;
    }

    public IntegerFilter getScale() {
        return scale;
    }

    public void setScale(IntegerFilter scale) {
        this.scale = scale;
    }

    public BooleanFilter getAllowNull() {
        return allowNull;
    }

    public void setAllowNull(BooleanFilter allowNull) {
        this.allowNull = allowNull;
    }

    public BooleanFilter getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(BooleanFilter primaryKey) {
        this.primaryKey = primaryKey;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public BooleanFilter getStop() {
        return stop;
    }

    public void setStop(BooleanFilter stop) {
        this.stop = stop;
    }

    public InstantFilter getCreateDate() {
        return createDate;
    }

    public void setCreateDate(InstantFilter createDate) {
        this.createDate = createDate;
    }

    public InstantFilter getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(InstantFilter updateDate) {
        this.updateDate = updateDate;
    }

    public InstantFilter getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(InstantFilter modifyDate) {
        this.modifyDate = modifyDate;
    }

    public LongFilter getDataCatalogId() {
        return dataCatalogId;
    }

    public void setDataCatalogId(LongFilter dataCatalogId) {
        this.dataCatalogId = dataCatalogId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataFieldsCriteria that = (DataFieldsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(fieldType, that.fieldType) &&
            Objects.equals(length, that.length) &&
            Objects.equals(scale, that.scale) &&
            Objects.equals(allowNull, that.allowNull) &&
            Objects.equals(primaryKey, that.primaryKey) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(stop, that.stop) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(modifyDate, that.modifyDate) &&
            Objects.equals(dataCatalogId, that.dataCatalogId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldName,
        fieldType,
        length,
        scale,
        allowNull,
        primaryKey,
        remarks,
        stop,
        createDate,
        updateDate,
        modifyDate,
        dataCatalogId
        );
    }

    @Override
    public String toString() {
        return "DataFieldsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
                (fieldType != null ? "fieldType=" + fieldType + ", " : "") +
                (length != null ? "length=" + length + ", " : "") +
                (scale != null ? "scale=" + scale + ", " : "") +
                (allowNull != null ? "allowNull=" + allowNull + ", " : "") +
                (primaryKey != null ? "primaryKey=" + primaryKey + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (stop != null ? "stop=" + stop + ", " : "") +
                (createDate != null ? "createDate=" + createDate + ", " : "") +
                (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
                (modifyDate != null ? "modifyDate=" + modifyDate + ", " : "") +
                (dataCatalogId != null ? "dataCatalogId=" + dataCatalogId + ", " : "") +
            "}";
    }

}
