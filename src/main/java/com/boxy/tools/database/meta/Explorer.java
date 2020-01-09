package com.boxy.tools.database.meta;

import com.boxy.tools.database.meta.entity.Database;

public interface Explorer {
    // 获取数据库完整信息（数据库名称，表，字段，主键，外键）
    Database getDatabase();
}
