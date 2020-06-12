package ru.otus.jdbc.mapper;

import java.util.Optional;

public interface JdbcMapper<T> {
    long insert(T objectData);

    long update(T objectData);

    long insertOrUpdate(T objectData);

    Optional<T> findById(long id);
}
