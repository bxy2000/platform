package com.boxy.platform.web.rest;

import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.service.DbToolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DbToolsResource {
    private final Logger log = LoggerFactory.getLogger(DbToolsResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DbToolsService dbToolsService;

    public DbToolsResource(
        DbToolsService dbToolsService
    ) {
        this.dbToolsService = dbToolsService;
    }

    @GetMapping("/db-tools/{dataCatalogId}/{databaseConnectionId}")
    public ResponseEntity<List<DataCatalog>> importAllData(@PathVariable Long dataCatalogId,
                                                           @PathVariable Long databaseConnectionId) {
        log.debug("REST request to import All Data : {} {}", dataCatalogId, databaseConnectionId);
        List<DataCatalog> entityList = dbToolsService.importAllData(dataCatalogId, databaseConnectionId);

        return ResponseEntity.ok().body(entityList);
    }

    @GetMapping("/db-tools/browse/{dataCatalogId}")
    public ResponseEntity<List<Map<String, Object>>> browseTable(@PathVariable Long dataCatalogId, Pageable pageable) {
        log.debug("REST request to browse data : {}", dataCatalogId);

        long total = dbToolsService.totalCount(dataCatalogId);
        List<Map<String, Object>> data = dbToolsService.browseTable(dataCatalogId, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(total));

        return ResponseEntity.ok().headers(headers).body(data);

    }
}
