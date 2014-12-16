package com.solidmobile.ws.server;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.solidmobile.commons.data.cursor.ForwardCursor;
import com.solidmobile.commons.data.cursor.ListForwardCursor;
import com.solidmobile.protocol.models.entity.Entity;
import com.solidmobile.protocol.models.entity.EntityTypeDefinition;
import com.solidmobile.protocol.models.entity.values.Value;
import com.solidmobile.protocol.models.entity.values.ValueFactory;
import com.solidmobile.server.ExecutionContext;
import com.solidmobile.server.data.adapter.DataSourceAdapter;
import com.solidmobile.server.data.exceptions.AdapterException;

import java.io.File;
import java.io.IOException;
import java.util.List;
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


    private List<Entity> getAllLines(EntityTypeDefinition entityTypeDefinition) {
        File file = new File("D:\\ws\\test.csv");
        List<Entity> entities = Lists.newArrayList();

        try {
            List<String> lines = Files.readLines(file, Charsets.UTF_8);
            for (String line : lines) {
                Map<String, Value<?>> values = Maps.newHashMap();
                List<String> valuesString = Splitter.on(";").splitToList(line);
                String id = valuesString.get(0);
                String name = valuesString.get(1);
                String active = valuesString.get(2);
                values.put("id", ValueFactory.create(Value.Type.INTEGER, id));
                values.put("name", ValueFactory.create(Value.Type.TEXT, name));
                values.put("active", ValueFactory.create(Value.Type.BOOLEAN, active.equals("1")));
                Entity entity = new Entity(id, entityTypeDefinition.getEntityType(), values);
                entities.add(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public ForwardCursor<Entity> findAll(ExecutionContext context, EntityTypeDefinition def) throws AdapterException {
        return new ListForwardCursor<Entity>(getAllLines(def));
    }

    @Override
    public Optional<Entity> find(ExecutionContext context, EntityTypeDefinition def, String id) throws AdapterException {
        List<Entity> allLines = getAllLines(def);
        for (Entity entity : allLines) {
            if (entity.getId().equals(id)) {
                return Optional.of(entity);
            }
        }
        return Optional.absent();
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
