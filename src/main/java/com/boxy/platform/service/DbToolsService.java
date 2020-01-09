package com.boxy.platform.service;

import com.boxy.platform.domain.*;
import com.boxy.platform.repository.*;
import com.boxy.platform.service.dto.DataCatalogCriteria;
import com.boxy.platform.service.dto.DataFieldsCriteria;
import com.boxy.platform.service.util.StringUtil;
import com.boxy.tools.database.meta.entity.Database;
import com.boxy.tools.database.meta.entity.Field;
import com.boxy.tools.database.meta.entity.ForeignKey;
import com.boxy.tools.database.meta.entity.Table;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class DbToolsService {
    private final Logger log = LoggerFactory.getLogger(DbToolsService.class);
    private final DatabaseConnectionRepository databaseConnectionRepository;
    private final DataCatalogRepository dataCatalogRepository;
    private final DataCatalogQueryService dataCatalogQueryService;
    private final DataPrimaryKeyRepository dataPrimaryKeyRepository;
    private final DataForeignKeyRepository dataForeignKeyRepository;
    private final DataFieldsRepository dataFieldsRepository;
    private final DataFieldsQueryService dataFieldsQueryService;
    private final DbToolsRepository dbToolsRepository;

    public DbToolsService(
        DatabaseConnectionRepository databaseConnectionRepository,
        DataCatalogRepository dataCatalogRepository,
        DataCatalogQueryService dataCatalogQueryService,
        DataPrimaryKeyRepository dataPrimaryKeyRepository,
        DataForeignKeyRepository dataForeignKeyRepository,
        DataFieldsRepository dataFieldsRepository,
        DataFieldsQueryService dataFieldsQueryService,
        DbToolsRepository dbToolsRepository
    ) {
        this.databaseConnectionRepository = databaseConnectionRepository;
        this.dataCatalogRepository = dataCatalogRepository;
        this.dataCatalogQueryService = dataCatalogQueryService;
        this.dataPrimaryKeyRepository = dataPrimaryKeyRepository;
        this.dataForeignKeyRepository = dataForeignKeyRepository;
        this.dataFieldsRepository = dataFieldsRepository;
        this.dataFieldsQueryService = dataFieldsQueryService;
        this.dbToolsRepository = dbToolsRepository;
    }

    public List<DataCatalog> importAllData(Long dataCatalogId, Long databaseConnectionId) {
        List<DataCatalog> result = new ArrayList<>();
        DatabaseConnection databaseConnection = this.databaseConnectionRepository.findById(databaseConnectionId).orElse(null);
        DataCatalog dataCatalog = this.dataCatalogRepository.findById(dataCatalogId).orElse(null);

        if (databaseConnection == null || dataCatalog == null)
            return result;

        String url = databaseConnection.getUrl();
        if (!StringUtil.isEmpty(databaseConnection.getParams())) {
            url += "?" + databaseConnection.getParams();
        }

        Database database = dbToolsRepository.getDatabase(databaseConnection.getType(), databaseConnection.getDriver(), url, databaseConnection.getDatabaseName(), databaseConnection.getUsername(), databaseConnection.getPassword());

        for (Table table : database.getTables()) {
            // 新增表
            DataCatalog newDataCatalog = new DataCatalog();
            newDataCatalog.setParent(dataCatalog);
            newDataCatalog.setTableName(table.getTableName());
            newDataCatalog.setRemarks(table.getRemarks());

            String title = StringUtil.isEmpty(table.getRemarks()) ? "" : table.getRemarks();
            title += "-" + table.getTableName();

            newDataCatalog.setTitle(title);
            newDataCatalog.setType("TABLE");

            dataCatalogRepository.save(newDataCatalog);
            // 新增主键

            if (table.getPrimaryKey().getPrimaryKeyName() != null || table.getPrimaryKey().getPrimaryKeyName() != "") {
                DataPrimaryKey dataPrimaryKey = new DataPrimaryKey();

                dataPrimaryKey.setDataCatalog(newDataCatalog);

                dataPrimaryKey.setName(table.getPrimaryKey().getPrimaryKeyName());
                dataPrimaryKey.setFields(table.getPrimaryKey().getPrimaryKeys().stream()
                    .collect(Collectors.joining(",")));
                dataPrimaryKey.setStop(false);
                dataPrimaryKey.setCreateDate(Instant.now());

                dataPrimaryKeyRepository.save(dataPrimaryKey);
            }
            // 新增外键
            for (ForeignKey foreignKey : table.getForeignKeys()) {
                DataForeignKey dataForeignKey = new DataForeignKey();
                dataForeignKey.setDataCatalog(newDataCatalog);

                dataForeignKey.setName(foreignKey.getForeignKeyName());
                dataForeignKey.setField(foreignKey.getForeignKeys().stream().collect(Collectors.joining(",")));
                dataForeignKey.setReferenceTable(foreignKey.getReferencesTableName());
                dataForeignKey.setReferenceField(foreignKey.getReferencesTablePrimaryKeys().stream().collect(Collectors.joining(",")));
                dataForeignKey.setStop(false);
                dataForeignKey.createDate(Instant.now());
                dataForeignKeyRepository.save(dataForeignKey);
            }

            // 新增数据列
            for (Field field : table.getFields()) {
                DataFields dataFields = new DataFields();

                dataFields.setDataCatalog(newDataCatalog);
                dataFields.setFieldName(field.getFieldName());
                dataFields.setFieldType(field.getFieldType());
                dataFields.setLength(field.getLength());
                dataFields.setScale(field.getScale());
                dataFields.setAllowNull(field.getNullable());
                dataFields.setRemarks(field.getRemarks());
                dataFields.setStop(false);
                dataFields.createDate(Instant.now());

                dataFieldsRepository.save(dataFields);
            }
        }

        DataCatalogCriteria dataCatalogCriteria = new DataCatalogCriteria();
        LongFilter parentIdFilter = new LongFilter();
        parentIdFilter.setEquals(dataCatalogId);
        dataCatalogCriteria.setParentId(parentIdFilter);

        result = this.dataCatalogQueryService.findByCriteria(dataCatalogCriteria);

        return result;
    }

    @Transactional(readOnly = true)
    public  List<Map<String, Object>>browseTable(Long dataCatalogId, Pageable pageable) {
        List<Map<String, Object>> result = new ArrayList<>();
        // 获取数据连接
        DataCatalog dataCatalog = dataCatalogRepository.findById(dataCatalogId).orElse(null);
        if(dataCatalog == null) {
            return result;
        }
        // DataCatalog parentCatalog = dataCatalog.getParent();
        // System.out.println(parentCatalog.getId() + " - " + parentCatalog.getTitle());
        DatabaseConnection databaseConnection = getDatabaseConnection(dataCatalog); //parentCatalog.getDbConnection();

        if(databaseConnection == null) {
            return result;
        }
        LongFilter dataCatalogIdFilter = new LongFilter();
        dataCatalogIdFilter.setEquals(dataCatalogId);

        DataFieldsCriteria dataFieldsCriteria = new DataFieldsCriteria();
        dataFieldsCriteria.setDataCatalogId(dataCatalogIdFilter);
        // 获取字段
        List<DataFields> fields = dataFieldsQueryService.findByCriteria(dataFieldsCriteria);
        result = dbToolsRepository.findTable(databaseConnection, dataCatalog.getTableName(), fields, pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public  long totalCount(Long dataCatalogId) {
        long result = 0;
        // 获取数据连接
        DataCatalog dataCatalog = dataCatalogRepository.findById(dataCatalogId).orElse(null);
        if(dataCatalog == null) {
            return result;
        }
        // DataCatalog parentCatalog = dataCatalog.getParent();

        DatabaseConnection databaseConnection = getDatabaseConnection(dataCatalog);//parentCatalog.getDbConnection();

        if(databaseConnection == null) {
            return result;
        }

        result = dbToolsRepository.findTotal(databaseConnection, dataCatalog.getTableName());
        return result;
    }

    private DatabaseConnection getDatabaseConnection(DataCatalog dataCatalog) {
        DataCatalog catalog = dataCatalog;
        while(catalog != null && catalog.getDbConnection() == null) {
            catalog = catalog.getParent();
        }

        if(catalog != null) {
            return catalog.getDbConnection();
        } else {
            return null;
        }
    }
}
