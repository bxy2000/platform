package com.boxy.platform.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 外键表
 */
@ApiModel(description = "外键表")
@Entity
@Table(name = "data_foreign_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataForeignKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "field")
    private String field;

    @Column(name = "reference_table")
    private String referenceTable;

    @Column(name = "reference_field")
    private String referenceField;

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
    @JsonIgnoreProperties("dataForeignKeys")
    private DataCatalog dataCatalog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataForeignKey name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public DataForeignKey field(String field) {
        this.field = field;
        return this;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getReferenceTable() {
        return referenceTable;
    }

    public DataForeignKey referenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
        return this;
    }

    public void setReferenceTable(String referenceTable) {
        this.referenceTable = referenceTable;
    }

    public String getReferenceField() {
        return referenceField;
    }

    public DataForeignKey referenceField(String referenceField) {
        this.referenceField = referenceField;
        return this;
    }

    public void setReferenceField(String referenceField) {
        this.referenceField = referenceField;
    }

    public String getRemarks() {
        return remarks;
    }

    public DataForeignKey remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean isStop() {
        return stop;
    }

    public DataForeignKey stop(Boolean stop) {
        this.stop = stop;
        return this;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public DataForeignKey createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public DataForeignKey updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Instant getModifyDate() {
        return modifyDate;
    }

    public DataForeignKey modifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public void setModifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
    }

    public DataCatalog getDataCatalog() {
        return dataCatalog;
    }

    public DataForeignKey dataCatalog(DataCatalog dataCatalog) {
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
        if (!(o instanceof DataForeignKey)) {
            return false;
        }
        return id != null && id.equals(((DataForeignKey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataForeignKey{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", field='" + getField() + "'" +
            ", referenceTable='" + getReferenceTable() + "'" +
            ", referenceField='" + getReferenceField() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", stop='" + isStop() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            "}";
    }
}
