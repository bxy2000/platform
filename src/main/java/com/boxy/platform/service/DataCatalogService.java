package com.boxy.platform.service;

import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.repository.DataCatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DataCatalog}.
 */
@Service
@Transactional
public class DataCatalogService {

    private final Logger log = LoggerFactory.getLogger(DataCatalogService.class);

    private final DataCatalogRepository dataCatalogRepository;

    public DataCatalogService(DataCatalogRepository dataCatalogRepository) {
        this.dataCatalogRepository = dataCatalogRepository;
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
        log.debug("Request to delete DataCatalog : {}", id);
        dataCatalogRepository.deleteById(id);
    }
}
