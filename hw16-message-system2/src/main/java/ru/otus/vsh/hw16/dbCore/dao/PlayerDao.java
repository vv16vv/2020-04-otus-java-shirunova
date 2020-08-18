package ru.otus.vsh.hw16.dbCore.dao;

import ru.otus.vsh.hw16.model.domain.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerDao extends Dao<Player> {
    Optional<Player> findByLogin(String login);
}
