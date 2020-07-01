package ru.otus.vsh.hw12.dbCore.dbService.api;

import ru.otus.vsh.hw12.dbCore.model.Model;
import ru.otus.vsh.hw12.dbCore.sessionmanager.SessionManager;

import java.util.Optional;

public interface DBService<T extends Model> {

    long saveObject(T t);

    long newObject(T t);

    void editObject(T t);

    Optional<T> getObject(long id);

    long doSaveObject(SessionManager sessionManager, T t);

    long doNewObject(SessionManager sessionManager, T t);

    void doEditObject(SessionManager sessionManager, T t);

    Optional<T> doGetObject(SessionManager sessionManager, long id);

}
