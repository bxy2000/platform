package com.boxy.platform.web.rest;

import com.boxy.platform.domain.DataForeignKey;
import com.boxy.platform.service.DataForeignKeyService;
import com.boxy.platform.web.rest.errors.BadRequestAlertException;
import com.boxy.platform.service.dto.DataForeignKeyCriteria;
import com.boxy.platform.service.DataForeignKeyQueryService;

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
 * REST controller for managing {@link com.boxy.platform.domain.DataForeignKey}.
 */
@RestController
@RequestMapping("/api")
public class DataForeignKeyResource {

    private final Logger log = LoggerFactory.getLogger(DataForeignKeyResource.class);

    private static final String ENTITY_NAME = "dataForeignKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataForeignKeyService dataForeignKeyService;

    private final DataForeignKeyQueryService dataForeignKeyQueryService;

    public DataForeignKeyResource(DataForeignKeyService dataForeignKeyService, DataForeignKeyQueryService dataForeignKeyQueryService) {
        this.dataForeignKeyService = dataForeignKeyService;
        this.dataForeignKeyQueryService = dataForeignKeyQueryService;
    }

    /**
     * {@code POST  /data-foreign-keys} : Create a new dataForeignKey.
     *
     * @param dataForeignKey the dataForeignKey to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataForeignKey, or with status {@code 400 (Bad Request)} if the dataForeignKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-foreign-keys")
    public ResponseEntity<DataForeignKey> createDataForeignKey(@RequestBody DataForeignKey dataForeignKey) throws URISyntaxException {
        log.debug("REST request to save DataForeignKey : {}", dataForeignKey);
        if (dataForeignKey.getId() != null) {
            throw new BadRequestAlertException("A new dataForeignKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataForeignKey result = dataForeignKeyService.save(dataForeignKey);
        return ResponseEntity.created(new URI("/api/data-foreign-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-foreign-keys} : Updates an existing dataForeignKey.
     *
     * @param dataForeignKey the dataForeignKey to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataForeignKey,
     * or with status {@code 400 (Bad Request)} if the dataForeignKey is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataForeignKey couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-foreign-keys")
    public ResponseEntity<DataForeignKey> updateDataForeignKey(@RequestBody DataForeignKey dataForeignKey) throws URISyntaxException {
        log.debug("REST request to update DataForeignKey : {}", dataForeignKey);
        if (dataForeignKey.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataForeignKey result = dataForeignKeyService.save(dataForeignKey);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataForeignKey.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-foreign-keys} : get all the dataForeignKeys.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataForeignKeys in body.
     */
    @GetMapping("/data-foreign-keys")
    public ResponseEntity<List<DataForeignKey>> getAllDataForeignKeys(DataForeignKeyCriteria criteria) {
        log.debug("REST request to get DataForeignKeys by criteria: {}", criteria);
        List<DataForeignKey> entityList = dataForeignKeyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /data-foreign-keys/count} : count all the dataForeignKeys.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/data-foreign-keys/count")
    public ResponseEntity<Long> countDataForeignKeys(DataForeignKeyCriteria criteria) {
        log.debug("REST request to count DataForeignKeys by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataForeignKeyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-foreign-keys/:id} : get the "id" dataForeignKey.
     *
     * @param id the id of the dataForeignKey to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataForeignKey, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-foreign-keys/{id}")
    public ResponseEntity<DataForeignKey> getDataForeignKey(@PathVariable Long id) {
        log.debug("REST request to get DataForeignKey : {}", id);
        Optional<DataForeignKey> dataForeignKey = dataForeignKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataForeignKey);
    }

    /**
     * {@code DELETE  /data-foreign-keys/:id} : delete the "id" dataForeignKey.
     *
     * @param id the id of the dataForeignKey to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-foreign-keys/{id}")
    public ResponseEntity<Void> deleteDataForeignKey(@PathVariable Long id) {
        log.debug("REST request to delete DataForeignKey : {}", id);
        dataForeignKeyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
