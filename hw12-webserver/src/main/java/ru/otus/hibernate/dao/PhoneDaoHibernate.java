package ru.otus.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.PhoneDao;
import ru.otus.core.dao.PhoneDaoException;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class PhoneDaoHibernate implements PhoneDao {
    private static final Logger logger = LoggerFactory.getLogger(PhoneDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public PhoneDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public Optional<PhoneDataSet> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(PhoneDataSet.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertPhone(PhoneDataSet phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(phone);
            return phone.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }

    @Override
    public void updatePhone(PhoneDataSet phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(phone);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(PhoneDataSet phone) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (phone.getId() > 0) {
                hibernateSession.merge(phone);
            } else {
                hibernateSession.persist(phone);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new PhoneDaoException(e);
        }
    }


    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
