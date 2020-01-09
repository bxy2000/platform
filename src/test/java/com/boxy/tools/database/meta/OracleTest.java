package com.boxy.tools.database.meta;

import com.boxy.platform.PlatformApp;
import com.boxy.tools.database.meta.entity.Database;
import com.boxy.tools.database.meta.entity.Table;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PlatformApp.class)
public class OracleTest {

    @Test
    public void testOracle() throws Exception {
        //type, String url, String databaseName, String userName, String password
        JdbcConfig jdbcConfig = new JdbcConfig(
            "ORACLE",
            "oracle.jdbc.driver.OracleDriver",
            "jdbc:oracle:thin:@10.10.10.164:1521:orcl",
            "orcl",
            "usr_zxbz",
            "kfdx#2018");

        Explorer explorer = ExplorerFactory.createExplorer(jdbcConfig);

        Database database = explorer.getDatabase();

        for(Table table : database.getTables()) {
            System.out.println(table.getTableName());
        }
    }
}
