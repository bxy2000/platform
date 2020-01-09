package com.boxy.platform.repository;

import com.boxy.platform.domain.DataForeignKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataForeignKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataForeignKeyRepository extends JpaRepository<DataForeignKey, Long>, JpaSpecificationExecutor<DataForeignKey> {

}
