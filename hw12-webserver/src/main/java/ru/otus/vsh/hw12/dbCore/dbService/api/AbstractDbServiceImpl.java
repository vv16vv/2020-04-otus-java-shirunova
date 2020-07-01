package ru.otus.vsh.hw12.dbCore.dbService.api;

import org.slf4j.Logger;
import ru.otus.vsh.hw12.dbCore.dao.Dao;
import ru.otus.vsh.hw12.dbCore.model.Model;
import ru.otus.vsh.hw12.dbCore.sessionmanager.SessionManager;

import java.util.Optional;

abstract public class AbstractDbServiceImpl<T extends Model> implements DBService<T> {
    private final Dao<T> dao;

    public AbstractDbServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    @Override
    public long newObject(T t) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return doNewObject(sessionManager,t);
            } catch (Exception e) {
                getLogger().error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public long doNewObject(SessionManager sessionManager, T t) {
        long id = dao.insert(t);
        sessionManager.commitSession();

        getLogger().info("created object with id = {}", id);
        return id;
    }

    @Override
    public void editObject(T t) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                doEditObject(sessionManager, t);
            } catch (Exception e) {
                getLogger().error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void doEditObject(SessionManager sessionManager, T t) {
        dao.update(t);
        sessionManager.commitSession();

        getLogger().info("edited object with id = {}", t.getId());
    }

    @Override
    public long saveObject(T t) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return doSaveObject(sessionManager,t);
            } catch (Exception e) {
                getLogger().error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public long doSaveObject(SessionManager sessionManager, T t) {
        dao.insertOrUpdate(t);
        long id = t.getId();
        sessionManager.commitSession();

        getLogger().info("created or edited object with id = {}", id);
        return id;
    }

    @Override
    public Optional<T> getObject(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return doGetObject(sessionManager,id);
            } catch (Exception e) {
                getLogger().error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<T> doGetObject(SessionManager sessionManager, long id) {
        Optional<T> optional = dao.findById(id);

        getLogger().info("object: {}", optional.orElse(null));
        return optional;
    }

    abstract protected Logger getLogger();
}
