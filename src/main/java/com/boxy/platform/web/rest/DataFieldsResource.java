package com.boxy.platform.web.rest;

import com.boxy.platform.domain.DataFields;
import com.boxy.platform.service.DataFieldsService;
import com.boxy.platform.web.rest.errors.BadRequestAlertException;
import com.boxy.platform.service.dto.DataFieldsCriteria;
import com.boxy.platform.service.DataFieldsQueryService;

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
 * REST controller for managing {@link com.boxy.platform.domain.DataFields}.
 */
@RestController
@RequestMapping("/api")
public class DataFieldsResource {

    private final Logger log = LoggerFactory.getLogger(DataFieldsResource.class);

    private static final String ENTITY_NAME = "dataFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataFieldsService dataFieldsService;

    private final DataFieldsQueryService dataFieldsQueryService;

    public DataFieldsResource(DataFieldsService dataFieldsService, DataFieldsQueryService dataFieldsQueryService) {
        this.dataFieldsService = dataFieldsService;
        this.dataFieldsQueryService = dataFieldsQueryService;
    }

    /**
     * {@code POST  /data-fields} : Create a new dataFields.
     *
     * @param dataFields the dataFields to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataFields, or with status {@code 400 (Bad Request)} if the dataFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-fields")
    public ResponseEntity<DataFields> createDataFields(@RequestBody DataFields dataFields) throws URISyntaxException {
        log.debug("REST request to save DataFields : {}", dataFields);
        if (dataFields.getId() != null) {
            throw new BadRequestAlertException("A new dataFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataFields result = dataFieldsService.save(dataFields);
        return ResponseEntity.created(new URI("/api/data-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-fields} : Updates an existing dataFields.
     *
     * @param dataFields the dataFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataFields,
     * or with status {@code 400 (Bad Request)} if the dataFields is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataFields couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-fields")
    public ResponseEntity<DataFields> updateDataFields(@RequestBody DataFields dataFields) throws URISyntaxException {
        log.debug("REST request to update DataFields : {}", dataFields);
        if (dataFields.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataFields result = dataFieldsService.save(dataFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataFields.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-fields} : get all the dataFields.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataFields in body.
     */
    @GetMapping("/data-fields")
    public ResponseEntity<List<DataFields>> getAllDataFields(DataFieldsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DataFields by criteria: {}", criteria);
        Page<DataFields> page = dataFieldsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /data-fields/count} : count all the dataFields.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/data-fields/count")
    public ResponseEntity<Long> countDataFields(DataFieldsCriteria criteria) {
        log.debug("REST request to count DataFields by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataFieldsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-fields/:id} : get the "id" dataFields.
     *
     * @param id the id of the dataFields to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataFields, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-fields/{id}")
    public ResponseEntity<DataFields> getDataFields(@PathVariable Long id) {
        log.debug("REST request to get DataFields : {}", id);
        Optional<DataFields> dataFields = dataFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataFields);
    }

    /**
     * {@code DELETE  /data-fields/:id} : delete the "id" dataFields.
     *
     * @param id the id of the dataFields to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-fields/{id}")
    public ResponseEntity<Void> deleteDataFields(@PathVariable Long id) {
        log.debug("REST request to delete DataFields : {}", id);
        dataFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
