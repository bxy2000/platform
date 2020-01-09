package com.boxy.platform.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.boxy.platform.domain.DatabaseConnection;
import com.boxy.platform.domain.*; // for static metamodels
import com.boxy.platform.repository.DatabaseConnectionRepository;
import com.boxy.platform.service.dto.DatabaseConnectionCriteria;

/**
 * Service for executing complex queries for {@link DatabaseConnection} entities in the database.
 * The main input is a {@link DatabaseConnectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DatabaseConnection} or a {@link Page} of {@link DatabaseConnection} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DatabaseConnectionQueryService extends QueryService<DatabaseConnection> {

    private final Logger log = LoggerFactory.getLogger(DatabaseConnectionQueryService.class);

    private final DatabaseConnectionRepository databaseConnectionRepository;

    public DatabaseConnectionQueryService(DatabaseConnectionRepository databaseConnectionRepository) {
        this.databaseConnectionRepository = databaseConnectionRepository;
    }

    /**
     * Return a {@link List} of {@link DatabaseConnection} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DatabaseConnection> findByCriteria(DatabaseConnectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DatabaseConnection> specification = createSpecification(criteria);
        return databaseConnectionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DatabaseConnection} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DatabaseConnection> findByCriteria(DatabaseConnectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DatabaseConnection> specification = createSpecification(criteria);
        return databaseConnectionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DatabaseConnectionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DatabaseConnection> specification = createSpecification(criteria);
        return databaseConnectionRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<DatabaseConnection> createSpecification(DatabaseConnectionCriteria criteria) {
        Specification<DatabaseConnection> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DatabaseConnection_.id));
            }
            if (criteria.getConnectionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConnectionName(), DatabaseConnection_.connectionName));
            }
            if (criteria.getHost() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHost(), DatabaseConnection_.host));
            }
            if (criteria.getPort() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPort(), DatabaseConnection_.port));
            }
            if (criteria.getDatabaseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatabaseName(), DatabaseConnection_.databaseName));
            }
            if (criteria.getParams() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParams(), DatabaseConnection_.params));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), DatabaseConnection_.type));
            }
            if (criteria.getDriver() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDriver(), DatabaseConnection_.driver));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), DatabaseConnection_.url));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), DatabaseConnection_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), DatabaseConnection_.password));
            }
            if (criteria.getTest() != null) {
                specification = specification.and(buildSpecification(criteria.getTest(), DatabaseConnection_.test));
            }
        }
        return specification;
    }
}
