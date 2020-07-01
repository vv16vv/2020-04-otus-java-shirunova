package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AddressDao;
import ru.otus.core.dao.AddressDaoException;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class AddressDaoHibernate implements AddressDao {
    private static final Logger logger = LoggerFactory.getLogger(AddressDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public AddressDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<AddressDataSet> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(AddressDataSet.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertAddress(AddressDataSet address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(address);
            return address.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public void updateAddress(AddressDataSet address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(address);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(AddressDataSet address) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (address.getId() > 0) {
                hibernateSession.merge(address);
            } else {
                hibernateSession.persist(address);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AddressDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
