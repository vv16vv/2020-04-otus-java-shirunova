package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entity;


    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entity) {
        this.entity = entity;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT * FROM %s", entity.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("SELECT * FROM %s WHERE %s = ?", entity.getName(), entity.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        var names = entity.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        var quantity = entity.getFieldsWithoutId().size();
        var values = new StringBuilder();
        for (int i = 0; i < quantity; i++) {
            if (i > 0) values.append(", ");
            values.append("?");
        }
        return String.format("INSERT INTO %s(%s) values(%s)",
                entity.getName(),
                names,
                values.toString());
    }

    @Override
    public String getUpdateSql() {
        var names = entity.getFieldsWithoutId().stream()
                .map(field -> String.format("%s = ?", field.getName()))
                .collect(Collectors.joining(", "));
        return String.format("UPDATE %s SET %s WHERE %s = ?", entity.getName(), names, entity.getIdField().getName());
    }
}
