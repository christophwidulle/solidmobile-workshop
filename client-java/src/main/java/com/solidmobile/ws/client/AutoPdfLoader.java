package com.solidmobile.ws.client;

import com.solidmobile.client.SolidClient;
import com.solidmobile.client.persistence.ClientDataSource;
import com.solidmobile.protocol.models.entity.ClientEntity;
import com.solidmobile.protocol.models.entity.EntityType;

/**
 * @author Christoph Widulle
 */
public class AutoPdfLoader {

    private final SolidClient solidClient;


    public AutoPdfLoader(SolidClient solidClient) {
        this.solidClient = solidClient;
    }

    public void load(EntityType entityType, long clientId) {

        ClientDataSource clientDataSource = solidClient.services().getDataSourceService().find(entityType.getDataSourceId()).get();
        ClientEntity clientEntity = clientDataSource.getEntityHandler().findByClientId(entityType, clientId);

        //LargeBinaryValue value = clientEntity.getValueAs("pdf");

        solidClient.services().getSyncService().updateLargeBinary(entityType, clientId, "pdf");


    }
}
