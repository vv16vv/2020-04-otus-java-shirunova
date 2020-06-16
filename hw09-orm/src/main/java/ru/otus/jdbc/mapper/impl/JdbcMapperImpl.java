package ru.otus.jdbc.mapper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.impl.types.ParseDbColumnType;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<T> dbExecutor;
    private final EntityClassMetaData<T> entity;
    private final EntitySQLMetaData sqlQueries;

    public JdbcMapperImpl(@Nonnull SessionManagerJdbc sessionManager,
                          @Nonnull DbExecutorImpl<T> dbExecutor,
                          @Nonnull EntityClassMetaData<T> entity,
                          @Nonnull EntitySQLMetaData sqlQueries) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.entity = entity;
        this.sqlQueries = sqlQueries;
    }

    @Nonnull
    public static <T> JdbcMapper<T> create(@Nonnull Class<T> clazz,
                                           @Nonnull SessionManagerJdbc sessionManager,
                                           @Nonnull DbExecutorImpl<T> dbExecutor) {
        var entity = EntityClassMetaDataImpl.create(clazz);
        var sqlQueries = new EntitySQLMetaDataImpl(entity);
        return new JdbcMapperImpl<>(sessionManager, dbExecutor, entity, sqlQueries);
    }


    @Override
    public void insert(T objectData) {
        try {
            var values = getValues(entity.getFieldsWithoutId(), objectData);
            var newId = dbExecutor.executeInsert(getConnection(), sqlQueries.getInsertSql(), values);
            entity.getIdField().set(objectData, newId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(T objectData) {
        try {
            var values = getValues(entity.getFieldsWithoutId(), objectData);
            values.add(getValue(entity.getIdField(), objectData));

            dbExecutor.executeUpdate(getConnection(), sqlQueries.getUpdateSql(), values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        try {
            var id = entity.getIdField().getLong(objectData);
            if (findById(id) == null)
                insert(objectData);
            else update(objectData);
        } catch (IllegalAccessException shouldNotOccur) {
            throw new IllegalStateException(shouldNotOccur);
        }
    }

    @Override
    public T findById(long id) {
        try {
            return dbExecutor.executeSelect(
                    getConnection(),
                    sqlQueries.getSelectByIdSql(),
                    id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                var object = entity.getConstructor().newInstance();
                                for (var field : entity.getAllFields()) {
                                    var columnLabel = field.getName();
                                    var value = ParseDbColumnType
                                            .parse(field.getType())
                                            .getValue(rs, columnLabel);
                                    field.set(object, value);
                                }
                                return object;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }).orElse(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    @Nonnull
    private List<Object> getValues(@Nonnull List<Field> fields, @Nonnull T objectData) {
        return fields.stream()
                .map(field -> getValue(field, objectData))
                .collect(Collectors.toList());
    }

    @Nonnull
    private Object getValue(@Nonnull Field field, @Nonnull T objectData) {
        try {
            return field.get(objectData);
        } catch (IllegalAccessException shouldNotOccur) {
            throw new IllegalStateException(shouldNotOccur);
        }
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
