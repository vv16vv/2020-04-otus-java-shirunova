package ru.otus.vsh.hw16.dbCore.dbService;

import ru.otus.vsh.hw16.dbCore.dbService.api.DBService;
import ru.otus.vsh.hw16.model.domain.Session;

import java.util.Optional;

public interface DBServiceSession extends DBService<Session> {
    Optional<Session> findBySessionId(String sessionId);
}
