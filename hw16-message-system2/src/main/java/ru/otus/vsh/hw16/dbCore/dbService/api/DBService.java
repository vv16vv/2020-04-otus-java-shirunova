package ru.otus.vsh.hw16.dbCore.dbService.api;

import ru.otus.vsh.hw16.domain.model.Model;

import java.util.List;
import java.util.Optional;

public interface DBService<T extends Model> {

    Long saveObject(T t);

    Long newObject(T t);

    void editObject(T t);

    Optional<T> getObject(Long id);

    List<T> findAll();

}
