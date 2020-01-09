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

import com.boxy.platform.domain.DataPrimaryKey;
import com.boxy.platform.domain.*; // for static metamodels
import com.boxy.platform.repository.DataPrimaryKeyRepository;
import com.boxy.platform.service.dto.DataPrimaryKeyCriteria;

/**
 * Service for executing complex queries for {@link DataPrimaryKey} entities in the database.
 * The main input is a {@link DataPrimaryKeyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataPrimaryKey} or a {@link Page} of {@link DataPrimaryKey} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataPrimaryKeyQueryService extends QueryService<DataPrimaryKey> {

    private final Logger log = LoggerFactory.getLogger(DataPrimaryKeyQueryService.class);

    private final DataPrimaryKeyRepository dataPrimaryKeyRepository;

    public DataPrimaryKeyQueryService(DataPrimaryKeyRepository dataPrimaryKeyRepository) {
        this.dataPrimaryKeyRepository = dataPrimaryKeyRepository;
    }

    /**
     * Return a {@link List} of {@link DataPrimaryKey} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataPrimaryKey> findByCriteria(DataPrimaryKeyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataPrimaryKey> specification = createSpecification(criteria);
        return dataPrimaryKeyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataPrimaryKey} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataPrimaryKey> findByCriteria(DataPrimaryKeyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataPrimaryKey> specification = createSpecification(criteria);
        return dataPrimaryKeyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataPrimaryKeyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataPrimaryKey> specification = createSpecification(criteria);
        return dataPrimaryKeyRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<DataPrimaryKey> createSpecification(DataPrimaryKeyCriteria criteria) {
        Specification<DataPrimaryKey> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataPrimaryKey_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DataPrimaryKey_.name));
            }
            if (criteria.getFields() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFields(), DataPrimaryKey_.fields));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), DataPrimaryKey_.remarks));
            }
            if (criteria.getStop() != null) {
                specification = specification.and(buildSpecification(criteria.getStop(), DataPrimaryKey_.stop));
            }
            if (criteria.getCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateDate(), DataPrimaryKey_.createDate));
            }
            if (criteria.getUpdateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateDate(), DataPrimaryKey_.updateDate));
            }
            if (criteria.getModifyDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifyDate(), DataPrimaryKey_.modifyDate));
            }
            if (criteria.getDataCatalogId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataCatalogId(),
                    root -> root.join(DataPrimaryKey_.dataCatalog, JoinType.LEFT).get(DataCatalog_.id)));
            }
        }
        return specification;
    }
}
