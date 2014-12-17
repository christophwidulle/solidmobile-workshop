package com.solidmobile.ws.server;

import com.solidmobile.protocol.models.config.DataSourceConfig;
import com.solidmobile.protocol.models.config.EntityTypeConfig;
import com.solidmobile.protocol.models.config.EntityTypeFilterConfig;
import com.solidmobile.protocol.models.entity.Attribute;
import com.solidmobile.protocol.models.entity.EntityType;
import com.solidmobile.protocol.models.entity.EntityTypeDefinition;
import com.solidmobile.protocol.models.entity.values.Value;
import com.solidmobile.server.core.SolidContext;
import com.solidmobile.server.data.adapter.jdbc.BaseJdbcDataSourceAdapterDefinition;
import com.solidmobile.server.data.adapter.jdbc.mysql.MySqlDataSourceAdapterDefinition;

/**
 * @author Christoph Widulle
 */
public class DataSources {


    private final SolidContext context;

    public DataSources(SolidContext context) {
        this.context = context;
    }


    public DataSourceConfig create() {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig("ws-data", "Workshop Data");
        dataSourceConfig.getTags().add("ws-data");


        final EntityTypeDefinition def = new EntityTypeDefinition(new EntityType("ws-data", "testdata"));

        final Attribute idAttr = Attribute.builder().id("id").valueType(Value.Type.INTEGER).primary(true).notNull().build();
        final Attribute nameAttr = Attribute.builder().id("name").valueType(Value.Type.TEXT).notNull().build();
        final Attribute activeAttr = Attribute.builder().id("active").valueType(Value.Type.BOOLEAN).notNull().build();

        def.setAttributes(idAttr, nameAttr, activeAttr);

        dataSourceConfig.addUsedEntityType(def);
        EntityTypeFilterConfig filterConfig = new EntityTypeFilterConfig(
                "com.solidmobile.server.data.filter.UserIdEntityTypeFilterPlugin"
        );
        filterConfig.addProperty("USER_ID_ATTRIBUTE", "userid");

        dataSourceConfig.getEntityTypeConfig("testdata")
                .getFilterConfig().add(filterConfig);

        dataSourceConfig.getEntityTypeConfig("testdata")
                .setContextSensitivity(EntityTypeConfig.ContextSensitivity.USER);



        addProps(dataSourceConfig);

        return dataSourceConfig;
    }


    public DataSourceConfig createCSV() {
        final DataSourceConfig dataSourceConfig = new DataSourceConfig("ws-csvdata", "Workshop CSV Data");
        dataSourceConfig.getTags().add("ws-csvdata");

        dataSourceConfig.setDataSourceAdapterDefinition(new CSVAdapterDefinition());

        final EntityTypeDefinition def = new EntityTypeDefinition(new EntityType("ws-csvdata", "csv"));

        final Attribute idAttr = Attribute.builder().id("id").valueType(Value.Type.INTEGER).primary(true).notNull().build();
        final Attribute nameAttr = Attribute.builder().id("name").valueType(Value.Type.TEXT).notNull().build();
        final Attribute activeAttr = Attribute.builder().id("active").valueType(Value.Type.BOOLEAN).notNull().build();

        def.setAttributes(idAttr, nameAttr, activeAttr);

        dataSourceConfig.addUsedEntityType(def);
        //dataSourceConfig.getEntityTypeConfig("testdata").getFilterConfig().add()

        return dataSourceConfig;
    }


    public void grantAppAccessTo(String datasourceId) {
        context.services().getApplicationService().grantAppDataSourceRight(WSServerApplication.APP_ID, datasourceId);

    }

    private static void addProps(final DataSourceConfig dataSourceConfig) {
        dataSourceConfig.setDataSourceAdapterDefinition(new MySqlDataSourceAdapterDefinition());

        dataSourceConfig.addAdapterParameter(BaseJdbcDataSourceAdapterDefinition.PROPERTY_SERVER, "127.0.0.1");
        dataSourceConfig.addAdapterParameter(BaseJdbcDataSourceAdapterDefinition.PROPERTY_PORT, "3306");
        dataSourceConfig.addAdapterParameter(BaseJdbcDataSourceAdapterDefinition.PROPERTY_DATABASE, "ws");
        dataSourceConfig.addAdapterParameter(BaseJdbcDataSourceAdapterDefinition.PROPERTY_USERNAME, "root");
        dataSourceConfig.addAdapterParameter(BaseJdbcDataSourceAdapterDefinition.PROPERTY_PASSWORD, "root");

        /*
        database.host=127.0.0.1
database.port=3306
#database.port=1433
database.user=root
database.password=root
database.name=wenco
         */
    }

}
