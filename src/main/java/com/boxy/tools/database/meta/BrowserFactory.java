package com.boxy.tools.database.meta;

import com.boxy.tools.database.meta.browser.DefaultBrowser;

public class BrowserFactory {
    public static Browser createExplorer(JdbcConfig config) {
        return new DefaultBrowser(config);
    }
}

