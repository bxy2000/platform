package com.boxy.tools.database.meta;

import java.util.List;
import java.util.Map;

public interface Browser {
    List<Map<String, Object>> findPage(int pageIndex, int pageSize, String tableName, List<String> fields);
    long totalCount(String table);
}
