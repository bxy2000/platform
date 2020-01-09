package com.boxy.tools.database.meta;

import com.boxy.tools.database.meta.explorer.MySqlExplorer;
import com.boxy.tools.database.meta.explorer.OracleExplorer;
import com.boxy.tools.database.meta.explorer.SqlServerExplorer;

public class ExplorerFactory {
    public static Explorer createExplorer(JdbcConfig config) {
        Explorer result = null;
        switch (config.getType()) {
            case "MYSQL":
                result = new MySqlExplorer(config);
                break;
            case "SQLSERVER":
                result = new SqlServerExplorer(config);
                break;
            case "ORACLE":
                result = new OracleExplorer(config);
                break;
        }

        return result;
    }
}
