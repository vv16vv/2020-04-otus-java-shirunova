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
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutorImpl<T> dbExecutor;
    private final EntityClassMetaData<T> entity;
    private final EntitySQLMetaData sqlMetaData;

    public JdbcMapperImpl(@Nonnull SessionManagerJdbc sessionManager,
                          @Nonnull DbExecutorImpl<T> dbExecutor,
                          @Nonnull EntityClassMetaData<T> entity,
                          @Nonnull EntitySQLMetaData sqlMetaData) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.entity = entity;
        this.sqlMetaData = sqlMetaData;
    }

    @Nonnull
    public static <T> JdbcMapper<T> initialize(@Nonnull Class<T> clazz,
                                               @Nonnull SessionManagerJdbc sessionManager,
                                               @Nonnull DbExecutorImpl<T> dbExecutor) {
        var entity = EntityClassMetaDataImpl.initialize(clazz);
        var sqlMetaData = new EntitySQLMetaDataImpl(entity);
        return new JdbcMapperImpl<>(sessionManager, dbExecutor, entity, sqlMetaData);
    }


    @Override
    public long insert(T objectData) {
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

            return dbExecutor.executeInsert(getConnection(), sqlMetaData.getInsertSql(), values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public long update(T objectData) {
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

            return dbExecutor.executeInsert(getConnection(), sqlMetaData.getUpdateSql(), values);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public long insertOrUpdate(T objectData) {
        try {
            var id = entity.getIdField().getLong(objectData);
            if(findById(id).isEmpty())
                return insert(objectData);
            else return update(objectData);
        } catch (IllegalAccessException shouldNotOccur) {
            throw new IllegalStateException(shouldNotOccur);
        }
    }

    @Override
    public Optional<T> findById(long id) {
        try {
            return dbExecutor.executeSelect(
                    getConnection(),
                    sqlMetaData.getSelectByIdSql(),
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
                                        // TODO all types
                                    }
                                }
                                return object;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();

    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
