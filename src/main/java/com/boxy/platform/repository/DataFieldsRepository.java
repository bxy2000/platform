package com.boxy.platform.repository;

import com.boxy.platform.domain.DataFields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataFields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataFieldsRepository extends JpaRepository<DataFields, Long>, JpaSpecificationExecutor<DataFields> {

}
