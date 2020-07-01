package ru.otus.dbCore.dao;

import ru.otus.dbCore.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findByLogin(String login);

    List<User> findAll();

    List<User> findByRole(String role);
}
