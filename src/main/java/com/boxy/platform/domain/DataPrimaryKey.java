package com.boxy.platform.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 主键表
 */
@ApiModel(description = "主键表")
@Entity
@Table(name = "data_primary_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataPrimaryKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fields")
    private String fields;

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
    @JsonIgnoreProperties("dataPrimaryKeys")
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

    public DataPrimaryKey name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFields() {
        return fields;
    }

    public DataPrimaryKey fields(String fields) {
        this.fields = fields;
        return this;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getRemarks() {
        return remarks;
    }

    public DataPrimaryKey remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean isStop() {
        return stop;
    }

    public DataPrimaryKey stop(Boolean stop) {
        this.stop = stop;
        return this;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public DataPrimaryKey createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public DataPrimaryKey updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Instant getModifyDate() {
        return modifyDate;
    }

    public DataPrimaryKey modifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public void setModifyDate(Instant modifyDate) {
        this.modifyDate = modifyDate;
    }

    public DataCatalog getDataCatalog() {
        return dataCatalog;
    }

    public DataPrimaryKey dataCatalog(DataCatalog dataCatalog) {
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
        if (!(o instanceof DataPrimaryKey)) {
            return false;
        }
        return id != null && id.equals(((DataPrimaryKey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataPrimaryKey{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fields='" + getFields() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", stop='" + isStop() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", modifyDate='" + getModifyDate() + "'" +
            "}";
    }
}
