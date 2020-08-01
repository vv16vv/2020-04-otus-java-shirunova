package ru.otus.vsh.hw14.hibernate.dao;

import com.google.common.collect.Lists;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw14.dbCore.dao.DaoException;
import ru.otus.vsh.hw14.dbCore.dao.UserDao;
import ru.otus.vsh.hw14.dbCore.model.User;
import ru.otus.vsh.hw14.hibernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.vsh.hw14.hibernate.sessionmanager.SessionManagerHibernate;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoHibernate extends AbstractDaoHibernate<User> implements UserDao {
    private final static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            var entityManager = sessionManager.getEntityManager();
            List<User> users = entityManager
                    .createNamedQuery(User.GET_USER_BY_LOGIN, User.class)
                    .setParameter("login", login)
                    .getResultList();
            if (users.isEmpty()) return Optional.empty();
            else return Optional.of(users.get(0));
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findByRole(String role) {
        try {
            var entityManager = sessionManager.getEntityManager();
            return entityManager
                    .createNamedQuery(User.GET_USER_BY_ROLE, User.class)
                    .setParameter("role", role)
                    .getResultList();
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
        }
        return Lists.newArrayList();
    }

    @Override
    public long insert(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(user);
            var phoneDaoHibernate = new PhoneDaoHibernate(sessionManager);
            for (var phone : user.getPhones()) {
                phoneDaoHibernate.insert(phone);
            }
            return user.getId();
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(user);
            var phoneDaoHibernate = new PhoneDaoHibernate(sessionManager);
            for (var phone : user.getPhones()) {
                phoneDaoHibernate.insertOrUpdate(phone);
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(@Nonnull User user) {
        if (user.getId() == 0)
            insert(user);
        else update(user);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
