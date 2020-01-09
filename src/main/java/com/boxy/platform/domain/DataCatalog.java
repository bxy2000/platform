package com.boxy.platform.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据目录
 */
@ApiModel(description = "数据目录")
@Entity
@Table(name = "data_catalog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "icon")
    private String icon;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "table_type")
    private String tableType;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "relation_graph")
    private String relationGraph;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataCatalog> children = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dataCatalogs")
    private DatabaseConnection dbConnection;

    @ManyToOne
    @JsonIgnoreProperties("children")
    private DataCatalog parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public DataCatalog title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public DataCatalog type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public DataCatalog icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTableName() {
        return tableName;
    }

    public DataCatalog tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public DataCatalog tableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public DataCatalog remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRelationGraph() {
        return relationGraph;
    }

    public DataCatalog relationGraph(String relationGraph) {
        this.relationGraph = relationGraph;
        return this;
    }

    public void setRelationGraph(String relationGraph) {
        this.relationGraph = relationGraph;
    }

    public Set<DataCatalog> getChildren() {
        return children;
    }

    public DataCatalog children(Set<DataCatalog> dataCatalogs) {
        this.children = dataCatalogs;
        return this;
    }

    public DataCatalog addChildren(DataCatalog dataCatalog) {
        this.children.add(dataCatalog);
        dataCatalog.setParent(this);
        return this;
    }

    public DataCatalog removeChildren(DataCatalog dataCatalog) {
        this.children.remove(dataCatalog);
        dataCatalog.setParent(null);
        return this;
    }

    public void setChildren(Set<DataCatalog> dataCatalogs) {
        this.children = dataCatalogs;
    }

    public DatabaseConnection getDbConnection() {
        return dbConnection;
    }

    public DataCatalog dbConnection(DatabaseConnection databaseConnection) {
        this.dbConnection = databaseConnection;
        return this;
    }

    public void setDbConnection(DatabaseConnection databaseConnection) {
        this.dbConnection = databaseConnection;
    }

    public DataCatalog getParent() {
        return parent;
    }

    public DataCatalog parent(DataCatalog dataCatalog) {
        this.parent = dataCatalog;
        return this;
    }

    public void setParent(DataCatalog dataCatalog) {
        this.parent = dataCatalog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataCatalog)) {
            return false;
        }
        return id != null && id.equals(((DataCatalog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataCatalog{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", icon='" + getIcon() + "'" +
            ", tableName='" + getTableName() + "'" +
            ", tableType='" + getTableType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", relationGraph='" + getRelationGraph() + "'" +
            "}";
    }
}
