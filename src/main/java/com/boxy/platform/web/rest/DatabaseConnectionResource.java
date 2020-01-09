package com.boxy.platform.web.rest;

import com.boxy.platform.domain.DatabaseConnection;
import com.boxy.platform.service.DatabaseConnectionService;
import com.boxy.platform.web.rest.errors.BadRequestAlertException;
import com.boxy.platform.service.dto.DatabaseConnectionCriteria;
import com.boxy.platform.service.DatabaseConnectionQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boxy.platform.domain.DatabaseConnection}.
 */
@RestController
@RequestMapping("/api")
public class DatabaseConnectionResource {

    private final Logger log = LoggerFactory.getLogger(DatabaseConnectionResource.class);

    private static final String ENTITY_NAME = "databaseConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatabaseConnectionService databaseConnectionService;

    private final DatabaseConnectionQueryService databaseConnectionQueryService;

    public DatabaseConnectionResource(DatabaseConnectionService databaseConnectionService, DatabaseConnectionQueryService databaseConnectionQueryService) {
        this.databaseConnectionService = databaseConnectionService;
        this.databaseConnectionQueryService = databaseConnectionQueryService;
    }

    /**
     * {@code POST  /database-connections} : Create a new databaseConnection.
     *
     * @param databaseConnection the databaseConnection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new databaseConnection, or with status {@code 400 (Bad Request)} if the databaseConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/database-connections")
    public ResponseEntity<DatabaseConnection> createDatabaseConnection(@RequestBody DatabaseConnection databaseConnection) throws URISyntaxException {
        log.debug("REST request to save DatabaseConnection : {}", databaseConnection);
        if (databaseConnection.getId() != null) {
            throw new BadRequestAlertException("A new databaseConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatabaseConnection result = databaseConnectionService.save(databaseConnection);
        return ResponseEntity.created(new URI("/api/database-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /database-connections} : Updates an existing databaseConnection.
     *
     * @param databaseConnection the databaseConnection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated databaseConnection,
     * or with status {@code 400 (Bad Request)} if the databaseConnection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the databaseConnection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/database-connections")
    public ResponseEntity<DatabaseConnection> updateDatabaseConnection(@RequestBody DatabaseConnection databaseConnection) throws URISyntaxException {
        log.debug("REST request to update DatabaseConnection : {}", databaseConnection);
        if (databaseConnection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatabaseConnection result = databaseConnectionService.save(databaseConnection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, databaseConnection.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /database-connections} : get all the databaseConnections.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of databaseConnections in body.
     */
    @GetMapping("/database-connections")
    public ResponseEntity<List<DatabaseConnection>> getAllDatabaseConnections(DatabaseConnectionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DatabaseConnections by criteria: {}", criteria);
        Page<DatabaseConnection> page = databaseConnectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /database-connections/count} : count all the databaseConnections.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/database-connections/count")
    public ResponseEntity<Long> countDatabaseConnections(DatabaseConnectionCriteria criteria) {
        log.debug("REST request to count DatabaseConnections by criteria: {}", criteria);
        return ResponseEntity.ok().body(databaseConnectionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /database-connections/:id} : get the "id" databaseConnection.
     *
     * @param id the id of the databaseConnection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the databaseConnection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/database-connections/{id}")
    public ResponseEntity<DatabaseConnection> getDatabaseConnection(@PathVariable Long id) {
        log.debug("REST request to get DatabaseConnection : {}", id);
        Optional<DatabaseConnection> databaseConnection = databaseConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(databaseConnection);
    }

    /**
     * {@code DELETE  /database-connections/:id} : delete the "id" databaseConnection.
     *
     * @param id the id of the databaseConnection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/database-connections/{id}")
    public ResponseEntity<Void> deleteDatabaseConnection(@PathVariable Long id) {
        log.debug("REST request to delete DatabaseConnection : {}", id);
        databaseConnectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
