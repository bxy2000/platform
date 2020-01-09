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

import com.boxy.platform.domain.DataFields;
import com.boxy.platform.domain.*; // for static metamodels
import com.boxy.platform.repository.DataFieldsRepository;
import com.boxy.platform.service.dto.DataFieldsCriteria;

/**
 * Service for executing complex queries for {@link DataFields} entities in the database.
 * The main input is a {@link DataFieldsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataFields} or a {@link Page} of {@link DataFields} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataFieldsQueryService extends QueryService<DataFields> {

    private final Logger log = LoggerFactory.getLogger(DataFieldsQueryService.class);

    private final DataFieldsRepository dataFieldsRepository;

    public DataFieldsQueryService(DataFieldsRepository dataFieldsRepository) {
        this.dataFieldsRepository = dataFieldsRepository;
    }

    /**
     * Return a {@link List} of {@link DataFields} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataFields> findByCriteria(DataFieldsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataFields> specification = createSpecification(criteria);
        return dataFieldsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataFields} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataFields> findByCriteria(DataFieldsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataFields> specification = createSpecification(criteria);
        return dataFieldsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataFieldsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataFields> specification = createSpecification(criteria);
        return dataFieldsRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<DataFields> createSpecification(DataFieldsCriteria criteria) {
        Specification<DataFields> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataFields_.id));
            }
            if (criteria.getFieldName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldName(), DataFields_.fieldName));
            }
            if (criteria.getFieldType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldType(), DataFields_.fieldType));
            }
            if (criteria.getLength() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLength(), DataFields_.length));
            }
            if (criteria.getScale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScale(), DataFields_.scale));
            }
            if (criteria.getAllowNull() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowNull(), DataFields_.allowNull));
            }
            if (criteria.getPrimaryKey() != null) {
                specification = specification.and(buildSpecification(criteria.getPrimaryKey(), DataFields_.primaryKey));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), DataFields_.remarks));
            }
            if (criteria.getStop() != null) {
                specification = specification.and(buildSpecification(criteria.getStop(), DataFields_.stop));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), DataFields_.createDate));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), DataFields_.updateDate));
            }
            if (criteria.getModifyDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyDate(), DataFields_.modifyDate));
            }
            if (criteria.getDataCatalogId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataCatalogId(),
                    root -> root.join(DataFields_.dataCatalog, JoinType.LEFT).get(DataCatalog_.id)));
            }
        }
        return specification;
    }
}
