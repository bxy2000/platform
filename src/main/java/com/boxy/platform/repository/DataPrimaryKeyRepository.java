package com.boxy.platform.repository;

import com.boxy.platform.domain.DataPrimaryKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataPrimaryKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataPrimaryKeyRepository extends JpaRepository<DataPrimaryKey, Long>, JpaSpecificationExecutor<DataPrimaryKey> {

}
