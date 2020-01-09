package com.boxy.platform.service;

import com.boxy.platform.domain.DataPrimaryKey;
import com.boxy.platform.repository.DataPrimaryKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DataPrimaryKey}.
 */
@Service
@Transactional
public class DataPrimaryKeyService {

    private final Logger log = LoggerFactory.getLogger(DataPrimaryKeyService.class);

    private final DataPrimaryKeyRepository dataPrimaryKeyRepository;

    public DataPrimaryKeyService(DataPrimaryKeyRepository dataPrimaryKeyRepository) {
        this.dataPrimaryKeyRepository = dataPrimaryKeyRepository;
    }

    /**
     * Save a dataPrimaryKey.
     *
     * @param dataPrimaryKey the entity to save.
     * @return the persisted entity.
     */
    public DataPrimaryKey save(DataPrimaryKey dataPrimaryKey) {
        log.debug("Request to save DataPrimaryKey : {}", dataPrimaryKey);
        return dataPrimaryKeyRepository.save(dataPrimaryKey);
    }

    /**
     * Get all the dataPrimaryKeys.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DataPrimaryKey> findAll() {
        log.debug("Request to get all DataPrimaryKeys");
        return dataPrimaryKeyRepository.findAll();
    }


    /**
     * Get one dataPrimaryKey by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataPrimaryKey> findOne(Long id) {
        log.debug("Request to get DataPrimaryKey : {}", id);
        return dataPrimaryKeyRepository.findById(id);
    }

    /**
     * Delete the dataPrimaryKey by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DataPrimaryKey : {}", id);
        dataPrimaryKeyRepository.deleteById(id);
    }
}
