package com.solidmobile.ws.server;

import com.solidmobile.protocol.models.config.DataSourceAdapterDefinition;
import com.solidmobile.server.core.Register;

@Register
public class CSVAdapterDefinition extends DataSourceAdapterDefinition {

    public static final String NAME = "Custom CSV Adapter";
    public static final String CLASS = "com.solidmobile.ws.server.CSVAdapter";

    public CSVAdapterDefinition() {
        super(NAME, CLASS, null);
    }
}
