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

import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.domain.*; // for static metamodels
import com.boxy.platform.repository.DataCatalogRepository;
import com.boxy.platform.service.dto.DataCatalogCriteria;

/**
 * Service for executing complex queries for {@link DataCatalog} entities in the database.
 * The main input is a {@link DataCatalogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataCatalog} or a {@link Page} of {@link DataCatalog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataCatalogQueryService extends QueryService<DataCatalog> {

    private final Logger log = LoggerFactory.getLogger(DataCatalogQueryService.class);

    private final DataCatalogRepository dataCatalogRepository;

    public DataCatalogQueryService(DataCatalogRepository dataCatalogRepository) {
        this.dataCatalogRepository = dataCatalogRepository;
    }

    /**
     * Return a {@link List} of {@link DataCatalog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataCatalog> findByCriteria(DataCatalogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataCatalog> specification = createSpecification(criteria);
        return dataCatalogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataCatalog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataCatalog> findByCriteria(DataCatalogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataCatalog> specification = createSpecification(criteria);
        return dataCatalogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataCatalogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataCatalog> specification = createSpecification(criteria);
        return dataCatalogRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<DataCatalog> createSpecification(DataCatalogCriteria criteria) {
        Specification<DataCatalog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataCatalog_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), DataCatalog_.title));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), DataCatalog_.type));
            }
            if (criteria.getIcon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcon(), DataCatalog_.icon));
            }
            if (criteria.getTableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableName(), DataCatalog_.tableName));
            }
            if (criteria.getTableType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableType(), DataCatalog_.tableType));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), DataCatalog_.remarks));
            }
            if (criteria.getChildrenId() != null) {
                specification = specification.and(buildSpecification(criteria.getChildrenId(),
                    root -> root.join(DataCatalog_.children, JoinType.LEFT).get(DataCatalog_.id)));
            }
            if (criteria.getDbConnectionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDbConnectionId(),
                    root -> root.join(DataCatalog_.dbConnection, JoinType.LEFT).get(DatabaseConnection_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(DataCatalog_.parent, JoinType.LEFT).get(DataCatalog_.id)));
            }
        }
        return specification;
    }
}
