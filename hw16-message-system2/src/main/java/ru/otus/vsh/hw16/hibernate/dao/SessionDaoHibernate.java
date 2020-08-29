package ru.otus.vsh.hw16.hibernate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.dao.SessionDao;
import ru.otus.vsh.hw16.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw16.model.domain.Session;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SessionDaoHibernate extends AbstractDaoHibernate<Session> implements SessionDao {

    public SessionDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Session.class);
    }

    @Override
    public Optional<Session> findBySessionId(String sessionId) {
        try {
            var entityManager = sessionManager.getEntityManager();
            List<Session> sessions = entityManager
                    .createNamedQuery(Session.GET_BY_SESSION_ID, Session.class)
                    .setParameter("sessionId", sessionId)
                    .getResultList();
            if (sessions.isEmpty()) return Optional.empty();
            else return Optional.of(sessions.get(0));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void insertOrUpdate(@Nonnull Session session) {
        if (session.getId() == 0)
            insert(session);
        else update(session);
    }

}
