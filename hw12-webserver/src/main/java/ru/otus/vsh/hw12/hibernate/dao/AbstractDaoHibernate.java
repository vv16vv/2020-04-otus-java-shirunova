package ru.otus.vsh.hw12.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import ru.otus.vsh.hw12.dbCore.dao.AddressDaoException;
import ru.otus.vsh.hw12.dbCore.dao.Dao;
import ru.otus.vsh.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw12.dbCore.model.Model;
import ru.otus.vsh.hw12.dbCore.sessionmanager.SessionManager;
import ru.otus.vsh.hw12.hibernate.sessionmanager.DatabaseSessionHibernate;

import java.util.Optional;

abstract public class AbstractDaoHibernate<T extends Model> implements Dao<T> {

    protected final SessionManagerHibernate sessionManager;
    protected final Class<T> modelClass;

    public AbstractDaoHibernate(SessionManagerHibernate sessionManager, Class<T> modelClass) {
        this.sessionManager = sessionManager;
        this.modelClass = modelClass;
    }

    @Override
    public Optional<T> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(modelClass, id));
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insert(T t) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(t);
            return t.getId();
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public void update(T t) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(t);
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(T t) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (t.getId() > 0) {
                hibernateSession.merge(t);
            } else {
                hibernateSession.persist(t);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    protected abstract Logger getLogger();
}
