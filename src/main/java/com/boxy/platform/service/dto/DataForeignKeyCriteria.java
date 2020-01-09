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
 * Criteria class for the {@link com.boxy.platform.domain.DataForeignKey} entity. This class is used
 * in {@link com.boxy.platform.web.rest.DataForeignKeyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /data-foreign-keys?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataForeignKeyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter field;

    private StringFilter referenceTable;

    private StringFilter referenceField;

    private StringFilter remarks;

    private BooleanFilter stop;

    private InstantFilter createDate;

    private InstantFilter updateDate;

    private InstantFilter modifyDate;

    private LongFilter dataCatalogId;

    public DataForeignKeyCriteria(){
    }

    public DataForeignKeyCriteria(DataForeignKeyCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.field = other.field == null ? null : other.field.copy();
        this.referenceTable = other.referenceTable == null ? null : other.referenceTable.copy();
        this.referenceField = other.referenceField == null ? null : other.referenceField.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.stop = other.stop == null ? null : other.stop.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.modifyDate = other.modifyDate == null ? null : other.modifyDate.copy();
        this.dataCatalogId = other.dataCatalogId == null ? null : other.dataCatalogId.copy();
    }

    @Override
    public DataForeignKeyCriteria copy() {
        return new DataForeignKeyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getField() {
        return field;
    }

    public void setField(StringFilter field) {
        this.field = field;
    }

    public StringFilter getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(StringFilter referenceTable) {
        this.referenceTable = referenceTable;
    }

    public StringFilter getReferenceField() {
        return referenceField;
    }

    public void setReferenceField(StringFilter referenceField) {
        this.referenceField = referenceField;
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
        final DataForeignKeyCriteria that = (DataForeignKeyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(field, that.field) &&
            Objects.equals(referenceTable, that.referenceTable) &&
            Objects.equals(referenceField, that.referenceField) &&
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
        name,
        field,
        referenceTable,
        referenceField,
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
        return "DataForeignKeyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (field != null ? "field=" + field + ", " : "") +
                (referenceTable != null ? "referenceTable=" + referenceTable + ", " : "") +
                (referenceField != null ? "referenceField=" + referenceField + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (stop != null ? "stop=" + stop + ", " : "") +
                (createDate != null ? "createDate=" + createDate + ", " : "") +
                (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
                (modifyDate != null ? "modifyDate=" + modifyDate + ", " : "") +
                (dataCatalogId != null ? "dataCatalogId=" + dataCatalogId + ", " : "") +
            "}";
    }

}
