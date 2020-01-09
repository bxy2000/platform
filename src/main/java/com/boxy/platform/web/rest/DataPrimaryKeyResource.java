package com.boxy.platform.web.rest;

import com.boxy.platform.domain.DataPrimaryKey;
import com.boxy.platform.service.DataPrimaryKeyService;
import com.boxy.platform.web.rest.errors.BadRequestAlertException;
import com.boxy.platform.service.dto.DataPrimaryKeyCriteria;
import com.boxy.platform.service.DataPrimaryKeyQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boxy.platform.domain.DataPrimaryKey}.
 */
@RestController
@RequestMapping("/api")
public class DataPrimaryKeyResource {

    private final Logger log = LoggerFactory.getLogger(DataPrimaryKeyResource.class);

    private static final String ENTITY_NAME = "dataPrimaryKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataPrimaryKeyService dataPrimaryKeyService;

    private final DataPrimaryKeyQueryService dataPrimaryKeyQueryService;

    public DataPrimaryKeyResource(DataPrimaryKeyService dataPrimaryKeyService, DataPrimaryKeyQueryService dataPrimaryKeyQueryService) {
        this.dataPrimaryKeyService = dataPrimaryKeyService;
        this.dataPrimaryKeyQueryService = dataPrimaryKeyQueryService;
    }

    /**
     * {@code POST  /data-primary-keys} : Create a new dataPrimaryKey.
     *
     * @param dataPrimaryKey the dataPrimaryKey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataPrimaryKey, or with status {@code 400 (Bad Request)} if the dataPrimaryKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-primary-keys")
    public ResponseEntity<DataPrimaryKey> createDataPrimaryKey(@RequestBody DataPrimaryKey dataPrimaryKey) throws URISyntaxException {
        log.debug("REST request to save DataPrimaryKey : {}", dataPrimaryKey);
        if (dataPrimaryKey.getId() != null) {
            throw new BadRequestAlertException("A new dataPrimaryKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataPrimaryKey result = dataPrimaryKeyService.save(dataPrimaryKey);
        return ResponseEntity.created(new URI("/api/data-primary-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-primary-keys} : Updates an existing dataPrimaryKey.
     *
     * @param dataPrimaryKey the dataPrimaryKey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataPrimaryKey,
     * or with status {@code 400 (Bad Request)} if the dataPrimaryKey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataPrimaryKey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-primary-keys")
    public ResponseEntity<DataPrimaryKey> updateDataPrimaryKey(@RequestBody DataPrimaryKey dataPrimaryKey) throws URISyntaxException {
        log.debug("REST request to update DataPrimaryKey : {}", dataPrimaryKey);
        if (dataPrimaryKey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataPrimaryKey result = dataPrimaryKeyService.save(dataPrimaryKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataPrimaryKey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-primary-keys} : get all the dataPrimaryKeys.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataPrimaryKeys in body.
     */
    @GetMapping("/data-primary-keys")
    public ResponseEntity<List<DataPrimaryKey>> getAllDataPrimaryKeys(DataPrimaryKeyCriteria criteria) {
        log.debug("REST request to get DataPrimaryKeys by criteria: {}", criteria);
        List<DataPrimaryKey> entityList = dataPrimaryKeyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /data-primary-keys/count} : count all the dataPrimaryKeys.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/data-primary-keys/count")
    public ResponseEntity<Long> countDataPrimaryKeys(DataPrimaryKeyCriteria criteria) {
        log.debug("REST request to count DataPrimaryKeys by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataPrimaryKeyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-primary-keys/:id} : get the "id" dataPrimaryKey.
     *
     * @param id the id of the dataPrimaryKey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataPrimaryKey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-primary-keys/{id}")
    public ResponseEntity<DataPrimaryKey> getDataPrimaryKey(@PathVariable Long id) {
        log.debug("REST request to get DataPrimaryKey : {}", id);
        Optional<DataPrimaryKey> dataPrimaryKey = dataPrimaryKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataPrimaryKey);
    }

    /**
     * {@code DELETE  /data-primary-keys/:id} : delete the "id" dataPrimaryKey.
     *
     * @param id the id of the dataPrimaryKey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-primary-keys/{id}")
    public ResponseEntity<Void> deleteDataPrimaryKey(@PathVariable Long id) {
        log.debug("REST request to delete DataPrimaryKey : {}", id);
        dataPrimaryKeyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
