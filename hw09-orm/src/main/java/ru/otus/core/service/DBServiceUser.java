package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    void editUser(User user);

    long newUser(User user);

    void saveUser(User user);

    Optional<User> getUser(long id);
}
