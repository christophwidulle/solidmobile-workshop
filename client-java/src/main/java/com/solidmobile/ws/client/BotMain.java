package com.solidmobile.ws.client;

import com.solidmobile.client.ISolidClient;
import com.solidmobile.client.JavaClientConfiguration;
import com.solidmobile.client.JavaClientConfigurationImpl;
import com.solidmobile.client.SolidClient;
import com.solidmobile.client.event.ClientDataSourcesUpdatedEvent;
import com.solidmobile.client.event.DataUpdateSyncEvent;
import com.solidmobile.client.event.DeviceAuthenticatedEvent;
import com.solidmobile.client.sqlite.FileSqliteJdbcDatabase;
import com.solidmobile.client.sqlite.SqliteDatabase;
import com.solidmobile.commons.event.EventListener;
import com.solidmobile.commons.log.ILogger;
import com.solidmobile.commons.log.LogLevel;
import com.solidmobile.commons.log.LoggerFactory;
import com.solidmobile.protocol.models.device.DeviceProfile;
import com.solidmobile.shared.transport.event.ConnectedEvent;

/**
 * @author Christoph Widulle
 */
public class BotMain {


    public static void main(final String[] args) {

        final WorkshopBot workshopBot = new WorkshopBot();
        workshopBot.init();
        Thread.yield();

    }


    static class WorkshopBot {

        private BotSolidClient solidClient;


        WorkshopBot() {
        }


        public void init() {


            final String mdsurl = "wss://localhost:1337";
            final String serverid = "solidserver";


            final DeviceProfile deviceProfile = new DeviceProfile(null, mdsurl, serverid);

            solidClient = new BotSolidClient();

            final JavaClientConfiguration clientConfiguration = new JavaClientConfigurationImpl();
            clientConfiguration.setMdsKeystorePath("keystore.jks");
            clientConfiguration.setMdsKeystorePassword("H5dGjeugmE");

            solidClient.init(Constants.APP_ID, clientConfiguration, new ISolidClient.InitListener() {
                @Override
                public void onInitCompleted() {
                    if (!isRegistered()) {

                        solidClient.internal().getMetaDataService().updateDeviceProfile(deviceProfile);

                        solidClient.internal().getClientEventBus().register(ConnectedEvent.class, new EventListener<ConnectedEvent>() {
                            @Override
                            public void on(final ConnectedEvent event) {
                                if (!isRegistered()) {
                                    solidClient.internal().getSecurityService().registerDevice();
                                }
                            }
                        });
                    }

                    solidClient.internal().getEventService().subscribe().onDeviceAuthenticated(new EventListener<DeviceAuthenticatedEvent>() {
                        @Override
                        public void on(DeviceAuthenticatedEvent event) {
                            log().info("bot is authenticated");
                            afterInit();
                        }
                    });

                    solidClient.services().getEventService().subscribe().onDataSourcesUpdated(new EventListener<ClientDataSourcesUpdatedEvent>() {
                        @Override
                        public void on(ClientDataSourcesUpdatedEvent event) {
                            new DataSourcesPrinter(solidClient).printAll();
                        }
                    });

                    solidClient.services().getEventService().subscribe().onDataUpdateSync(new EventListener<DataUpdateSyncEvent>() {
                        @Override
                        public void on(DataUpdateSyncEvent event) {
                            new DataSourcesPrinter(solidClient).printAll();
                            for (Long clientId : event.getClientIds()) {
                                //new NameSteamer(solidClient).rename(event.getEntityType(), clientId);
                            }
                        }
                    });


                }


                @Override
                public void onInitFailed(final Exception e) {
                    System.out.println(e);
                }
            });

        }

        protected boolean isRegistered() {
            return solidClient.services().getSecurityService().isRegistered();
        }

        protected ILogger log() {
            return solidClient.services().getClientLogger();
        }


        protected void afterInit() {
            //new EndlessMessageSender(solidClient).start();


        }


    }


    static class BotSolidClient extends SolidClient {


        @Override
        protected LoggerFactory createLoggerFactory() {
            LoggerFactory factory = super.createLoggerFactory();
            factory.setLogLevel(LogLevel.Debug);
            return factory;
        }

        @Override
        protected SqliteDatabase createSqliteDatabase() {
            return new FileSqliteJdbcDatabase(loggerFactory, "D:\\ws\\");
        }

    }

}
