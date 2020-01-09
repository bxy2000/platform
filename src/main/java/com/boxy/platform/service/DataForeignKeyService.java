package com.boxy.platform.service;

import com.boxy.platform.domain.DataForeignKey;
import com.boxy.platform.repository.DataForeignKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DataForeignKey}.
 */
@Service
@Transactional
public class DataForeignKeyService {

    private final Logger log = LoggerFactory.getLogger(DataForeignKeyService.class);

    private final DataForeignKeyRepository dataForeignKeyRepository;

    public DataForeignKeyService(DataForeignKeyRepository dataForeignKeyRepository) {
        this.dataForeignKeyRepository = dataForeignKeyRepository;
    }

    /**
     * Save a dataForeignKey.
     *
     * @param dataForeignKey the entity to save.
     * @return the persisted entity.
     */
    public DataForeignKey save(DataForeignKey dataForeignKey) {
        log.debug("Request to save DataForeignKey : {}", dataForeignKey);
        return dataForeignKeyRepository.save(dataForeignKey);
    }

    /**
     * Get all the dataForeignKeys.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DataForeignKey> findAll() {
        log.debug("Request to get all DataForeignKeys");
        return dataForeignKeyRepository.findAll();
    }


    /**
     * Get one dataForeignKey by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataForeignKey> findOne(Long id) {
        log.debug("Request to get DataForeignKey : {}", id);
        return dataForeignKeyRepository.findById(id);
    }

    /**
     * Delete the dataForeignKey by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DataForeignKey : {}", id);
        dataForeignKeyRepository.deleteById(id);
    }
}
