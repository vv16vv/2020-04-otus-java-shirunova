package ru.otus.dbCore.dao;

import ru.otus.dbCore.model.Model;
import ru.otus.dbCore.sessionmanager.SessionManager;

import java.util.Optional;

public interface Dao<T extends Model> {
    Optional<T> findById(long id);

    long insert(T t);

    void update(T t);

    void insertOrUpdate(T t);

    SessionManager getSessionManager();
}
