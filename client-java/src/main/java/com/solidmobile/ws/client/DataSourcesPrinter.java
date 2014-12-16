package com.solidmobile.ws.client;

import com.google.common.collect.ImmutableList;
import com.solidmobile.client.SolidClient;
import com.solidmobile.client.persistence.ClientDataSource;
import com.solidmobile.commons.data.cursor.ClientCursor;
import com.solidmobile.commons.log.ILogger;
import com.solidmobile.protocol.models.entity.ClientEntity;
import com.solidmobile.protocol.models.entity.EntityType;

/**
 * @author Christoph Widulle
 */
public class DataSourcesPrinter {

    private final SolidClient solidClient;

    public DataSourcesPrinter(SolidClient solidClient) {
        this.solidClient = solidClient;
    }

    public void printAll() {
        ILogger log = solidClient.services().getClientLogger();

        ImmutableList<? extends ClientDataSource> allDS = solidClient.services().getDataSourceService().findAll();
        for (ClientDataSource clientDataSource : allDS) {
            log.info("==== DS = " + clientDataSource.getDataSourceConfig().getDisplayName());
            for (EntityType entityType : clientDataSource.getEntityTypes()) {
                log.info("--- ET: " + entityType.getId());
                ClientCursor<? extends ClientEntity> allCursor = clientDataSource.getEntityHandler().findAllCursor(entityType);
                for (ClientEntity clientEntity : allCursor) {
                    log.info(EntityPrinter.pretty(clientEntity));
                }
            }
        }
    }
}
