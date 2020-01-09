package com.boxy.platform.repository;

import com.boxy.platform.domain.DatabaseConnection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DatabaseConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Long>, JpaSpecificationExecutor<DatabaseConnection> {

}
