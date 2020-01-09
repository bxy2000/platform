package com.boxy.platform.service;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.repository.DataCatalogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link UserService}.
 */
@SpringBootTest(classes = PlatformApp.class)
@Transactional
public class DbToolsServiceIT {
    @Autowired
    private DataCatalogRepository dataCatalogRepository;

    @Autowired
    private DbToolsService dbToolsService;

    @Test
    @Transactional
    public void assertThatUserMustExistToResetPassword() {

//        List<DataCatalog> dataCatalogList = dataCatalogRepository.findAll();
//        System.out.println(dataCatalogList);
//        SimpleDatasource simpleDatasource = new SimpleDatasource(
//            Dialect.MYSQL,
//            "jdbc:mysql://localhost:3306/school?useSSL=false",
//            "school",
//            "root",
//            "root_888"
//        );
//
//        simpleDatasource.testDriver();
//
//        DefaultExplorer explorer = new DefaultExplorer(simpleDatasource);
//
//        Database database = explorer.getDatabase();
//
//        System.out.println(database);
//
//        simpleDatasource.close();
    }

    @Test
    @Transactional
    public void aaa() {
//        this.dbToolsService.importAllData(21L, 1L);
    }
}
