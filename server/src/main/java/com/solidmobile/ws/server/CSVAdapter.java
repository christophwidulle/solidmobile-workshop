package com.solidmobile.ws.server;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.solidmobile.commons.data.cursor.ForwardCursor;
import com.solidmobile.protocol.models.entity.Entity;
import com.solidmobile.protocol.models.entity.EntityTypeDefinition;
import com.solidmobile.server.ExecutionContext;
import com.solidmobile.server.data.adapter.DataSourceAdapter;
import com.solidmobile.server.data.exceptions.AdapterException;

import java.util.Map;

/**
 * @author Christoph Widulle
 */
public class CSVAdapter implements DataSourceAdapter {

    @Override
    public void init(Map<String, String> adapterParameters, String datasourceId) throws AdapterException {

    }

    @Override
    public ImmutableList<EntityTypeDefinition> findAvailableEntityTypeDefinitions() throws AdapterException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean ping() throws AdapterException {
        return true;
    }

    @Override
    public ForwardCursor<Entity> findAll(ExecutionContext context, EntityTypeDefinition def) throws AdapterException {
        return null;
    }

    @Override
    public Optional<Entity> find(ExecutionContext context, EntityTypeDefinition def, String id) throws AdapterException {
        return null;
    }

    @Override
    public Entity insert(ExecutionContext context, EntityTypeDefinition def, Entity entity) throws AdapterException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity update(ExecutionContext context, EntityTypeDefinition def, Entity entity) throws AdapterException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(ExecutionContext context, EntityTypeDefinition def, String id) throws AdapterException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int deleteAll(ExecutionContext context, EntityTypeDefinition def) throws AdapterException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
