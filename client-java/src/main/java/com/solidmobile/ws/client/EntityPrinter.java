package com.solidmobile.ws.client;

import com.solidmobile.protocol.models.entity.ClientEntity;
import com.solidmobile.protocol.models.entity.values.Value;

import java.util.Map;

/**
 * @author Christoph Widulle
 */
public class EntityPrinter {


    public static String pretty(ClientEntity clientEntity) {
        StringBuilder b = new StringBuilder();
        b.append("id=").append(clientEntity.getId()).append(" | ");
        b.append("rev=").append(clientEntity.getRevision()).append(" | ");
        //b.append(ClientEntity.CommitState.asString(clientEntity.getCommitState())).append(" | ");
        for (Map.Entry<String, Value<?>> entry : clientEntity.getValues().entrySet()) {
            b.append(entry.getKey()).append("=").append(entry.getValue().valueAsString()).append(", ");
        }
        b.append(" | ");


        return b.toString();
    }
}
