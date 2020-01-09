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

import com.boxy.platform.domain.DataForeignKey;
import com.boxy.platform.domain.*; // for static metamodels
import com.boxy.platform.repository.DataForeignKeyRepository;
import com.boxy.platform.service.dto.DataForeignKeyCriteria;

/**
 * Service for executing complex queries for {@link DataForeignKey} entities in the database.
 * The main input is a {@link DataForeignKeyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataForeignKey} or a {@link Page} of {@link DataForeignKey} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataForeignKeyQueryService extends QueryService<DataForeignKey> {

    private final Logger log = LoggerFactory.getLogger(DataForeignKeyQueryService.class);

    private final DataForeignKeyRepository dataForeignKeyRepository;

    public DataForeignKeyQueryService(DataForeignKeyRepository dataForeignKeyRepository) {
        this.dataForeignKeyRepository = dataForeignKeyRepository;
    }

    /**
     * Return a {@link List} of {@link DataForeignKey} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataForeignKey> findByCriteria(DataForeignKeyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataForeignKey> specification = createSpecification(criteria);
        return dataForeignKeyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataForeignKey} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataForeignKey> findByCriteria(DataForeignKeyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataForeignKey> specification = createSpecification(criteria);
        return dataForeignKeyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataForeignKeyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataForeignKey> specification = createSpecification(criteria);
        return dataForeignKeyRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<DataForeignKey> createSpecification(DataForeignKeyCriteria criteria) {
        Specification<DataForeignKey> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataForeignKey_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DataForeignKey_.name));
            }
            if (criteria.getField() != null) {
                specification = specification.and(buildStringSpecification(criteria.getField(), DataForeignKey_.field));
            }
            if (criteria.getReferenceTable() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceTable(), DataForeignKey_.referenceTable));
            }
            if (criteria.getReferenceField() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceField(), DataForeignKey_.referenceField));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), DataForeignKey_.remarks));
            }
            if (criteria.getStop() != null) {
                specification = specification.and(buildSpecification(criteria.getStop(), DataForeignKey_.stop));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), DataForeignKey_.createDate));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), DataForeignKey_.updateDate));
            }
            if (criteria.getModifyDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyDate(), DataForeignKey_.modifyDate));
            }
            if (criteria.getDataCatalogId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataCatalogId(),
                    root -> root.join(DataForeignKey_.dataCatalog, JoinType.LEFT).get(DataCatalog_.id)));
            }
        }
        return specification;
    }
}
