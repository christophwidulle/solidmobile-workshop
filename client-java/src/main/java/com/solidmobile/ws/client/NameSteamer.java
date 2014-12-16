package com.solidmobile.ws.client;

import com.solidmobile.client.SolidClient;
import com.solidmobile.client.persistence.ClientDataSource;
import com.solidmobile.protocol.models.entity.ClientEntity;
import com.solidmobile.protocol.models.entity.ClientSolidEntity;
import com.solidmobile.protocol.models.entity.EntityType;

/**
 * @author Christoph Widulle
 */
public class NameSteamer {

    private final SolidClient solidClient;


    public NameSteamer(SolidClient solidClient) {
        this.solidClient = solidClient;
    }

    public void rename(EntityType entityType, long clientId) {

        ClientDataSource clientDataSource = solidClient.services().getDataSourceService().find(entityType.getDataSourceId()).get();
        ClientEntity clientEntity = clientDataSource.getEntityHandler().findByClientId(entityType, clientId);
        ClientSolidEntity entity = clientEntity.modify().valueText("name", "Robin").set();
        clientDataSource.getEntityHandler().save(entity);
        solidClient.services().getSyncService().commit(entityType, clientId);

    }
}
