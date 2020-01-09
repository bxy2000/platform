package com.boxy.tools.database.meta;

import com.boxy.platform.PlatformApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@SpringBootTest(classes = PlatformApp.class)
public class DataSourceTest {
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws Exception {
        // 获取配置的数据源
        // DataSource dataSource = applicationContext.getBean(DataSource.class);

        Connection conn = dataSource.getConnection();

        // 查看配置数据源信息
        //System.out.println(dataSource);
        //System.out.println(dataSource.getClass().getName());
        //System.out.println(dataSourceProperties);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root_888");
//        PreparedStatement statement = connection.prepareStatement("select * from students");
//        ResultSet resultSet = statement.executeQuery();
//        while(resultSet.next()){
//            System.out.println(resultSet.getString("student_name"));
//        }
        connection.close();
        //执行SQL,输出查到的数据
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        List<?> resultList = jdbcTemplate.queryForList("select * from test");
//        System.out.println("===>>>>>>>>>>>" + JSON.toJSONString(resultList));
    }
}
