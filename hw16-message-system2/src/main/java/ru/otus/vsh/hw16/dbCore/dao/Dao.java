package ru.otus.vsh.hw16.dbCore.dao;

import ru.otus.vsh.hw16.dbCore.model.Model;
import ru.otus.vsh.hw16.dbCore.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Model> {
    Optional<T> findById(long id);

    List<T> findAll();

    long insert(T t);

    void update(T t);

    void insertOrUpdate(T t);

    SessionManager getSessionManager();
}
