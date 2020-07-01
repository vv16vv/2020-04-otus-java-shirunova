package ru.otus.hibernate.dao;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.dao.UserDao;
import ru.otus.dbCore.model.User;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

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
    public List<User> findAll() {
        try {
            var entityManager = sessionManager.getEntityManager();
            return entityManager
                    .createNamedQuery(User.GET_ALL_USERS, User.class)
                    .getResultList();
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
        }
        return Lists.newArrayList();
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
    public void insertOrUpdate(@Nonnull User user) {
        if (user.getId() > 0)
            insert(user);
        else update(user);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
