package ru.otus.jdbc.mapper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDaoException;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import java.sql.Connection;
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
    public static <T> JdbcMapper<T> initialize(@Nonnull Class<T> clazz,
                                               @Nonnull SessionManagerJdbc sessionManager,
                                               @Nonnull DbExecutorImpl<T> dbExecutor) {
        var entity = EntityClassMetaDataImpl.initialize(clazz);
        var sqlQueries = new EntitySQLMetaDataImpl(entity);
        return new JdbcMapperImpl<>(sessionManager, dbExecutor, entity, sqlQueries);
    }


    @Override
    public void insert(T objectData) {
        try {
            var values = entity.getFieldsWithoutId().stream()
                    .map(field -> {
                        try {
                            return field.get(objectData);
                        } catch (IllegalAccessException shouldNotOccur) {
                            throw new IllegalStateException(shouldNotOccur);
                        }
                    })
                    .collect(Collectors.toUnmodifiableList());

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
            var values = entity.getFieldsWithoutId().stream()
                    .map(field -> {
                        try {
                            return field.get(objectData);
                        } catch (IllegalAccessException shouldNotOccur) {
                            throw new IllegalStateException(shouldNotOccur);
                        }
                    })
                    .collect(Collectors.toList());
            values.add(entity.getIdField().get(objectData));

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
                                    switch (field.getType().toString()) {
                                        case "class java.lang.String":
                                            field.set(object, rs.getString(columnLabel));
                                            break;
                                        case "class java.lang.Integer":
                                        case "int":
                                            field.set(object, rs.getInt(columnLabel));
                                            break;
                                        case "class java.lang.Long":
                                        case "long":
                                            field.set(object, rs.getLong(columnLabel));
                                            break;
                                        // TODO all types
                                    }
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

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
