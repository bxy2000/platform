package com.boxy.platform.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 数据表字段
 */
@ApiModel(description = "数据表字段")
@Entity
@Table(name = "data_fields")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "length")
    private Integer length;

    @Column(name = "scale")
    private Integer scale;

    @Column(name = "allow_null")
    private Boolean allowNull;

    @Column(name = "primary_key")
    private Boolean primaryKey;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "stop")
    private Boolean stop;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "modify_date")
    private Instant modifyDate;

    @ManyToOne
    @JsonIgnoreProperties("dataFields")
    private DataCatalog dataCatalog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public DataFields fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public DataFields fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getLength() {
        return length;
    }

    public DataFields length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getScale() {
        return scale;
    }

    public DataFields scale(Integer scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Boolean isAllowNull() {
        return allowNull;
    }

    public DataFields allowNull(Boolean allowNull) {
        this.allowNull = allowNull;
        return this;
    }

    public void setAllowNull(Boolean allowNull) {
        this.allowNull = allowNull;
    }

    public Boolean isPrimaryKey() {
        return primaryKey;
    }

    public DataFields primaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getRemarks() {
        return remarks;
    }

    public DataFields remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean isStop() {
        return stop;
    }

    public DataFields stop(Boolean stop) {
        this.stop = stop;
        return this;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public DataFields createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public DataFields updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Instant getModifyDate() {
        return modifyDate;
    }

    public DataFields modifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public void setModifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
    }

    public DataCatalog getDataCatalog() {
        return dataCatalog;
    }

    public DataFields dataCatalog(DataCatalog dataCatalog) {
        this.dataCatalog = dataCatalog;
        return this;
    }

    public void setDataCatalog(DataCatalog dataCatalog) {
        this.dataCatalog = dataCatalog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataFields)) {
            return false;
        }
        return id != null && id.equals(((DataFields) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataFields{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", length=" + getLength() +
            ", scale=" + getScale() +
            ", allowNull='" + isAllowNull() + "'" +
            ", primaryKey='" + isPrimaryKey() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", stop='" + isStop() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            "}";
    }
}
