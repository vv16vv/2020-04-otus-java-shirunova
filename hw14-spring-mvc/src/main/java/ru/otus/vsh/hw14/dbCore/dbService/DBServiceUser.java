package ru.otus.vsh.hw14.dbCore.dbService;

import ru.otus.vsh.hw14.dbCore.dbService.api.DBService;
import ru.otus.vsh.hw14.dbCore.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser extends DBService<User> {
    Optional<User> findByLogin(String login);

    List<User> findByRole(String role);
}
