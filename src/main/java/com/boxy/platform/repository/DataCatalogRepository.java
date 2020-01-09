package com.boxy.platform.repository;

import com.boxy.platform.domain.DataCatalog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataCatalog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataCatalogRepository extends JpaRepository<DataCatalog, Long>, JpaSpecificationExecutor<DataCatalog> {

}
