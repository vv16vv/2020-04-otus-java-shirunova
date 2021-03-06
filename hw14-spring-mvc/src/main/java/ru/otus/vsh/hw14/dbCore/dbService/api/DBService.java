package ru.otus.vsh.hw14.dbCore.dbService.api;

import ru.otus.vsh.hw14.dbCore.model.Model;
import ru.otus.vsh.hw14.dbCore.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface DBService<T extends Model> {

    Long saveObject(T t);

    Long newObject(T t);

    void editObject(T t);

    Optional<T> getObject(Long id);

    List<T> findAll();

}
