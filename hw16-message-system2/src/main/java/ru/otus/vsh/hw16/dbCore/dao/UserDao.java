package ru.otus.vsh.hw16.dbCore.dao;

import ru.otus.vsh.hw16.dbCore.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findByLogin(String login);

    List<User> findAll();

    List<User> findByRole(String role);
}
