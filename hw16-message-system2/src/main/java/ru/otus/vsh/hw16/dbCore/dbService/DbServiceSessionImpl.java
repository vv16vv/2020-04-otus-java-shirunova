package ru.otus.vsh.hw16.dbCore.dbService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw16.dbCore.dao.SessionDao;
import ru.otus.vsh.hw16.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw16.domain.model.Session;

import java.util.Optional;

@Slf4j
@Repository
public class DbServiceSessionImpl extends AbstractDbServiceImpl<Session> implements DBServiceSession {
    private final SessionDao sessionDao;

    public DbServiceSessionImpl(SessionDao sessionDao) {
        super(sessionDao);
        this.sessionDao = sessionDao;
    }

    @Override
    public Optional<Session> findBySessionId(String sessionId) {
        return executeInSession((sm, id) -> {
            var session = sessionDao.findBySessionId(id);
            sm.commitSession();

            log.info("found session with id = {}: {}", id, session);
            return session;
        }, sessionId);
    }

}
