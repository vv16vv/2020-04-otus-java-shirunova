package ru.otus.vsh.hw16.dbCore.dao;

import ru.otus.vsh.hw16.model.domain.Session;

import java.util.Optional;

public interface SessionDao extends Dao<Session> {
    Optional<Session> findBySessionId(String sessionId);
}
