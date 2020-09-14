package ru.otus.vsh.hw16.dbCore.dao;

import ru.otus.vsh.hw16.domain.model.Player;

import java.util.Optional;

public interface PlayerDao extends Dao<Player> {
    Optional<Player> findByLogin(String login);
}
