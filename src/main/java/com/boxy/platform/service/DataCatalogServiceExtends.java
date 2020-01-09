package com.boxy.platform.service;

import com.boxy.platform.domain.*;
import com.boxy.platform.repository.DataCatalogRepository;
import com.boxy.platform.repository.DataFieldsRepository;
import com.boxy.platform.repository.DataForeignKeyRepository;
import com.boxy.platform.repository.DataPrimaryKeyRepository;
import com.boxy.platform.service.dto.DataCatalogCriteria;
import com.boxy.platform.service.util.StringUtil;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DataCatalog}.
 */
@Service
@Transactional
public class DataCatalogServiceExtends {

    private final Logger log = LoggerFactory.getLogger(DataCatalogServiceExtends.class);

    private final DataCatalogRepository dataCatalogRepository;
    private final DataFieldsRepository dataFieldsRepository;
    private final DataPrimaryKeyRepository dataPrimaryKeyRepository;
    private final DataForeignKeyRepository dataForeignKeyRepository;

    private final DataCatalogQueryService dataCatalogQueryService;

    public DataCatalogServiceExtends(
        DataCatalogRepository dataCatalogRepository,
        DataFieldsRepository dataFieldsRepository,
        DataPrimaryKeyRepository dataPrimaryKeyRepository,
        DataForeignKeyRepository dataForeignKeyRepository,
        DataCatalogQueryService dataCatalogQueryService
    ) {
        this.dataCatalogRepository = dataCatalogRepository;
        this.dataFieldsRepository = dataFieldsRepository;
        this.dataPrimaryKeyRepository = dataPrimaryKeyRepository;
        this.dataForeignKeyRepository = dataForeignKeyRepository;
        this.dataCatalogQueryService = dataCatalogQueryService;
    }

    /**
     * Save a dataCatalog.
     *
     * @param dataCatalog the entity to save.
     * @return the persisted entity.
     */
    public DataCatalog save(DataCatalog dataCatalog) {
        log.debug("Request to save DataCatalog : {}", dataCatalog);
        return dataCatalogRepository.save(dataCatalog);
    }

    /**
     * Get all the dataCatalogs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DataCatalog> findAll() {
        log.debug("Request to get all DataCatalogs");
        return dataCatalogRepository.findAll();
    }


    /**
     * Get one dataCatalog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataCatalog> findOne(Long id) {
        log.debug("Request to get DataCatalog : {}", id);
        return dataCatalogRepository.findById(id);
    }

    /**
     * Delete the dataCatalog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        // 获取孩子节点
//        LongFilter longFilter = new LongFilter();
//        longFilter.setEquals(id);
//        DataCatalogCriteria catalogCriteria = new DataCatalogCriteria();
//        catalogCriteria.setParentId(longFilter);
//        List<DataCatalog> result = dataCatalogQueryService.findByCriteria(catalogCriteria);
//        for (DataCatalog item: result
//             ) {
//            // dataCatalogRepository.deleteById(dataCatalog.getId());
//            delete(item.getId());
//        }

        DataCatalog dataCatalog = dataCatalogRepository.findById(id).orElse(null);
        if (dataCatalog == null) return;

        for (DataCatalog catalog : dataCatalog.getChildren()) {
            delete(catalog.getId());
        }

        if ("TABLE".equals(dataCatalog.getType())) {
            deletePrimaryKey(id);
            deleteForeignKey(id);
            deleteFields(id);
        }

        dataCatalogRepository.deleteById(id);
    }

    public void deleteBat(String ids) {
        if(StringUtil.isEmpty(ids)) return;

        String[] token = ids.split(",");

        if(token == null || token.length < 1) return;

        for(String id: token) {
            delete(Long.parseLong(id));
        }
    }

    private void deletePrimaryKey(Long tableId) {
        DataCatalog dataCatalog = new DataCatalog();
        dataCatalog.setId(tableId);

        DataPrimaryKey dataPrimaryKey = new DataPrimaryKey();
        dataPrimaryKey.setDataCatalog(dataCatalog);

        Example<DataPrimaryKey> example = Example.of(dataPrimaryKey);

        List<DataPrimaryKey> list = dataPrimaryKeyRepository.findAll(example);

        list.forEach(u -> {
            this.dataPrimaryKeyRepository.deleteById(u.getId());
        });
    }

    private void deleteForeignKey(Long tableId) {
        DataCatalog dataCatalog = new DataCatalog();
        dataCatalog.setId(tableId);

        DataForeignKey dataForeignKey = new DataForeignKey();
        dataForeignKey.setDataCatalog(dataCatalog);

        Example<DataForeignKey> example = Example.of(dataForeignKey);

        List<DataForeignKey> list = dataForeignKeyRepository.findAll(example);

        list.forEach(u -> {
            this.dataForeignKeyRepository.deleteById(u.getId());
        });
    }

    private void deleteFields(Long tableId) {
        DataCatalog dataCatalog = new DataCatalog();
        dataCatalog.setId(tableId);

        DataFields dataFields = new DataFields();
        dataFields.setDataCatalog(dataCatalog);

        Example<DataFields> example = Example.of(dataFields);

        List<DataFields> list = dataFieldsRepository.findAll(example);

        list.forEach(u -> {
            this.dataFieldsRepository.deleteById(u.getId());
        });
    }

    public String findRelationship(Long connectionId) {
        // 获取数据表及字段
        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.setId(connectionId);

        DataCatalog dataCatalog = new DataCatalog();
        dataCatalog.setDbConnection(dbConnection);

        Example<DataCatalog> dataCatalogExample = Example.of(dataCatalog);

        List<DataCatalog> dataCatalogs = dataCatalogRepository.findAll(dataCatalogExample);

        if (dataCatalogs.size() < 1) {
            return "";
        }

        StringBuilder tableAndFields = new StringBuilder();
        StringBuilder relationShip = new StringBuilder();

        for (DataCatalog item : dataCatalogs.get(0).getChildren()) {
            tableAndFields.append(findTableAndFields(item));
            relationShip.append(findForeignKeyRelationship(item));
        }

        return tableAndFields.toString() + "\r\n" + relationShip.toString();
    }

    private String findTableAndFields(DataCatalog dataCatalog) {
        StringBuilder sb = new StringBuilder();

        String tableName = dataCatalog.getTableName();
        sb.append("[" + tableName + "|\r\n");

        DataFields dataFields = new DataFields();
        dataFields.setDataCatalog(dataCatalog);
        Example<DataFields> dataFieldsExample = Example.of(dataFields);

        for (DataFields item : dataFieldsRepository.findAll(dataFieldsExample)) {
            sb.append(item.getFieldName())
                .append("\r\n");
        }
        sb.append("]\r\n");

        return sb.toString();
    }

    private String findForeignKeyRelationship(DataCatalog dataCatalog) {
        StringBuilder sb = new StringBuilder();

        String tableName = dataCatalog.getTableName();

        DataForeignKey dataForeignKey = new DataForeignKey();
        dataForeignKey.setDataCatalog(dataCatalog);

        Example<DataForeignKey> dataForeignKeyExample = Example.of(dataForeignKey);

        for (DataForeignKey item : dataForeignKeyRepository.findAll(dataForeignKeyExample)) {
            String referenceTableName = item.getReferenceTable();

            sb.append("[")
                .append(tableName)
                .append("]")
                .append("->")
                .append("[")
                .append(referenceTableName)
                .append("]\r\n");
        }

        return sb.toString();
    }
}
