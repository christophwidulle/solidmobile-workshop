package com.solidmobile.ws.server;

import com.solidmobile.commons.log.ILogger;
import com.solidmobile.mds.protocol.message.PayloadMessage;
import com.solidmobile.protocol.models.config.DataSourceConfig;
import com.solidmobile.server.core.Register;
import com.solidmobile.server.core.SolidContext;
import com.solidmobile.server.message.MessagePayloadTypeHandler;
import com.solidmobile.server.meta.app.SolidServerApplication;

/**
 * @author Sven Jacobs
 */
@Register
public class WSServerApplication extends SolidServerApplication {

    public static final String APP_ID = "SM-WS";
    private ILogger log;

    @Override
    public void onInitialize(final SolidContext context) {
        initLogging(context);
        log.info("Server initialisiert.");


        DataSources dataSources = new DataSources(context);

        DataSourceConfig dataSourceConfig = dataSources.create();
        context.services().getDataSourceService().install(dataSourceConfig);
        dataSources.grantAppAccessTo(dataSourceConfig.getDataSourceId());


        DataSourceConfig csvDataSourceConfig = dataSources.createCSV();
        context.services().getDataSourceService().install(csvDataSourceConfig);
        dataSources.grantAppAccessTo(csvDataSourceConfig.getDataSourceId());

    }

    @Override
    public void onStart(final SolidContext context) {
        initLogging(context);


        context.services().getTransportService()
                .registerMessagePayloadTypeHandler(APP_ID, "endlessPing", new MessagePayloadTypeHandler() {
                    @Override
                    public void onHandle(PayloadMessage payloadMessage) {
                      //  log.info(payloadMessage.getPayload());
                        System.out.println(payloadMessage.getPayload());
                    }
                });


        log.info("Server gestartet.");
    }

    @Override
    public void onStop(final SolidContext context) {
    }

    @Override
    public void onUpgrade(final SolidContext context, final int oldVersion) {
    }

    @Override
    public String getAppId() {
        return APP_ID;
    }

    @Override
    public String getAppName() {
        return "SolidMobile-Workshop";
    }

    @Override
    public int getAppVersion() {
        return 1;
    }

    @Override
    public String getAppVersionDisplay() {
        return "1.0.0";
    }

    private void initLogging(final SolidContext context) {
        if (log == null) {
            log = context.loggerFactory().createLogger(WSServerApplication.class);
        }
    }
}
