package ru.otus.vsh.hw16.dbCore.dbService.api;

import lombok.extern.slf4j.Slf4j;
import ru.otus.vsh.hw16.dbCore.dao.Dao;
import ru.otus.vsh.hw16.dbCore.sessionmanager.SessionManager;
import ru.otus.vsh.hw16.domain.model.Model;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Slf4j
abstract public class AbstractDbServiceImpl<T extends Model> implements DBService<T> {
    private final Dao<T> dao;

    public AbstractDbServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    protected <P, R> R executeInSession(BiFunction<SessionManager, P, R> actions, P p) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return actions.apply(sessionManager, p);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Long newObject(T t) {
        return executeInSession(this::doNewObject, t);
    }

    protected Long doNewObject(SessionManager sessionManager, T t) {
        Long id = dao.insert(t);
        sessionManager.commitSession();

        log.info("created object with id = {}", id);
        return id;
    }

    @Override
    public void editObject(T t) {
        executeInSession(this::doEditObject, t);
    }

    protected T doEditObject(SessionManager sessionManager, T t) {
        dao.update(t);
        sessionManager.commitSession();

        log.info("edited object with id = {}", t.getId());
        return null;
    }

    @Override
    public Long saveObject(T t) {
        return executeInSession(this::doSaveObject, t);
    }

    protected Long doSaveObject(SessionManager sessionManager, T t) {
        dao.insertOrUpdate(t);
        Long id = t.getId();
        sessionManager.commitSession();

        log.info("created or edited object with id = {}", id);
        return id;
    }

    @Override
    public Optional<T> getObject(Long id) {
        return executeInSession(this::doGetObject, id);
    }

    protected Optional<T> doGetObject(SessionManager sessionManager, Long id) {
        Optional<T> optional = dao.findById(id);

        log.info("object: {}", optional.orElse(null));
        return optional;
    }

    @Override
    public List<T> findAll() {
        return executeInSession((sm, notUsed) -> {
            var objects = dao.findAll();
            sm.commitSession();

            log.info("found all objects {}", objects.toString());
            return objects;
        }, "");
    }

}
