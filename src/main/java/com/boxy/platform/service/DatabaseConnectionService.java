package com.boxy.platform.service;

import com.boxy.platform.domain.DatabaseConnection;
import com.boxy.platform.repository.DatabaseConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DatabaseConnection}.
 */
@Service
@Transactional
public class DatabaseConnectionService {

    private final Logger log = LoggerFactory.getLogger(DatabaseConnectionService.class);

    private final DatabaseConnectionRepository databaseConnectionRepository;

    public DatabaseConnectionService(DatabaseConnectionRepository databaseConnectionRepository) {
        this.databaseConnectionRepository = databaseConnectionRepository;
    }

    /**
     * Save a databaseConnection.
     *
     * @param databaseConnection the entity to save.
     * @return the persisted entity.
     */
    public DatabaseConnection save(DatabaseConnection databaseConnection) {
        log.debug("Request to save DatabaseConnection : {}", databaseConnection);
        return databaseConnectionRepository.save(databaseConnection);
    }

    /**
     * Get all the databaseConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DatabaseConnection> findAll(Pageable pageable) {
        log.debug("Request to get all DatabaseConnections");
        return databaseConnectionRepository.findAll(pageable);
    }


    /**
     * Get one databaseConnection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatabaseConnection> findOne(Long id) {
        log.debug("Request to get DatabaseConnection : {}", id);
        return databaseConnectionRepository.findById(id);
    }

    /**
     * Delete the databaseConnection by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DatabaseConnection : {}", id);
        databaseConnectionRepository.deleteById(id);
    }
}
