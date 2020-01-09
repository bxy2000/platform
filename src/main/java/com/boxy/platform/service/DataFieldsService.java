package com.boxy.platform.service;

import com.boxy.platform.domain.DataFields;
import com.boxy.platform.repository.DataFieldsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DataFields}.
 */
@Service
@Transactional
public class DataFieldsService {

    private final Logger log = LoggerFactory.getLogger(DataFieldsService.class);

    private final DataFieldsRepository dataFieldsRepository;

    public DataFieldsService(DataFieldsRepository dataFieldsRepository) {
        this.dataFieldsRepository = dataFieldsRepository;
    }

    /**
     * Save a dataFields.
     *
     * @param dataFields the entity to save.
     * @return the persisted entity.
     */
    public DataFields save(DataFields dataFields) {
        log.debug("Request to save DataFields : {}", dataFields);
        return dataFieldsRepository.save(dataFields);
    }

    /**
     * Get all the dataFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DataFields> findAll(Pageable pageable) {
        log.debug("Request to get all DataFields");
        return dataFieldsRepository.findAll(pageable);
    }


    /**
     * Get one dataFields by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataFields> findOne(Long id) {
        log.debug("Request to get DataFields : {}", id);
        return dataFieldsRepository.findById(id);
    }

    /**
     * Delete the dataFields by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DataFields : {}", id);
        dataFieldsRepository.deleteById(id);
    }
}
