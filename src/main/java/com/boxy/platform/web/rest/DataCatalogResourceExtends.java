package com.boxy.platform.web.rest;

import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.service.DataCatalogQueryService;
import com.boxy.platform.service.DataCatalogServiceExtends;
import com.boxy.platform.service.dto.DataCatalogCriteria;
import com.boxy.platform.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link DataCatalog}.
 */
@RestController
@RequestMapping("/api/ext")
public class DataCatalogResourceExtends {

    private final Logger log = LoggerFactory.getLogger(DataCatalogResourceExtends.class);

    private static final String ENTITY_NAME = "dataCatalog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataCatalogServiceExtends dataCatalogServiceExtends;

    private final DataCatalogQueryService dataCatalogQueryService;

    public DataCatalogResourceExtends(DataCatalogServiceExtends dataCatalogServiceExtends, DataCatalogQueryService dataCatalogQueryService) {
        this.dataCatalogServiceExtends = dataCatalogServiceExtends;
        this.dataCatalogQueryService = dataCatalogQueryService;
    }

    /**
     * {@code POST  /data-catalogs} : Create a new dataCatalog.
     *
     * @param dataCatalog the dataCatalog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataCatalog, or with status {@code 400 (Bad Request)} if the dataCatalog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-catalogs")
    public ResponseEntity<DataCatalog> createDataCatalog(@RequestBody DataCatalog dataCatalog) throws URISyntaxException {
        log.debug("REST request to save DataCatalog : {}", dataCatalog);
        if (dataCatalog.getId() != null) {
            throw new BadRequestAlertException("A new dataCatalog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataCatalog result = dataCatalogServiceExtends.save(dataCatalog);
        return ResponseEntity.created(new URI("/api/data-catalogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-catalogs} : Updates an existing dataCatalog.
     *
     * @param dataCatalog the dataCatalog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataCatalog,
     * or with status {@code 400 (Bad Request)} if the dataCatalog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataCatalog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-catalogs")
    public ResponseEntity<DataCatalog> updateDataCatalog(@RequestBody DataCatalog dataCatalog) throws URISyntaxException {
        log.debug("REST request to update DataCatalog : {}", dataCatalog);
        if (dataCatalog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataCatalog result = dataCatalogServiceExtends.save(dataCatalog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataCatalog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-catalogs} : get all the dataCatalogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataCatalogs in body.
     */
    @GetMapping("/data-catalogs")
    public ResponseEntity<List<DataCatalog>> getAllDataCatalogs(DataCatalogCriteria criteria) {
        log.debug("REST request to get DataCatalogs by criteria: {}", criteria);
        List<DataCatalog> entityList = dataCatalogQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /data-catalogs/count} : count all the dataCatalogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/data-catalogs/count")
    public ResponseEntity<Long> countDataCatalogs(DataCatalogCriteria criteria) {
        log.debug("REST request to count DataCatalogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataCatalogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-catalogs/:id} : get the "id" dataCatalog.
     *
     * @param id the id of the dataCatalog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataCatalog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-catalogs/{id}")
    public ResponseEntity<DataCatalog> getDataCatalog(@PathVariable Long id) {
        log.debug("REST request to get DataCatalog : {}", id);
        Optional<DataCatalog> dataCatalog = dataCatalogServiceExtends.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataCatalog);
    }

    /**
     * {@code DELETE  /data-catalogs/:id} : delete the "id" dataCatalog.
     *
     * @param id the id of the dataCatalog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-catalogs/{id}")
    public ResponseEntity<Void> deleteDataCatalog(@PathVariable Long id) {
        log.debug("REST request to delete DataCatalog : {}", id);
        dataCatalogServiceExtends.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/data-catalogs/deleteBat")
    public ResponseEntity<Void> deleteBatDataCatalog(@RequestBody String ids) {
        log.debug("REST request to bat delete DataCatalog : {}", ids);
        dataCatalogServiceExtends.deleteBat(ids);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, ids)).build();
    }

    @GetMapping("/relationship/{connectionId}")
    public ResponseEntity<Map<String, String>> getRelationship(@PathVariable Long connectionId) {
        log.debug("REST request to get Relationship : {}", connectionId);

        String result = dataCatalogServiceExtends.findRelationship(connectionId);

        Map<String, String> map = new HashMap<>();
        map.put("result", result);
        return ResponseEntity.ok().body(map);
    }
}
