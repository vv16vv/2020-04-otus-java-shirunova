package ru.otus.vsh.hw16.dbCore.dbService;

import ru.otus.vsh.hw16.dbCore.dbService.api.DBService;
import ru.otus.vsh.hw16.model.domain.Player;

import java.util.List;
import java.util.Optional;

public interface DBServicePlayer extends DBService<Player> {
    Optional<Player> findByLogin(String login);
}
