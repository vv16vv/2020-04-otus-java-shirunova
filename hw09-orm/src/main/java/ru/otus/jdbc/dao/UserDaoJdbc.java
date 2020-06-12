package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.impl.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Optional.of;

public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManager sessionManager;
    private final JdbcMapper<User> userMapper;

    private UserDaoJdbc(
            @Nonnull SessionManager sessionManager,
            @Nonnull JdbcMapper<User> userMapper ) {
        this.sessionManager = sessionManager;
        this.userMapper = userMapper;
    }

    @Nonnull
    public static UserDao initialize(
            @Nonnull SessionManagerJdbc sessionManager,
            @Nonnull DbExecutorImpl<User> dbExecutor){
        return new UserDaoJdbc(sessionManager, JdbcMapperImpl.initialize(User.class, sessionManager, dbExecutor));
    }

    @Override
    @Nonnull
    public Optional<User> findById(long id) {
        return Optional.ofNullable(userMapper.findById(id)) ;
    }

    @Override
    public long insertUser(@Nonnull User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(@Nonnull User user) {
        userMapper.update(user);
    }

    @Override
    public void insertOrUpdate(@Nonnull User user) {
        userMapper.insertOrUpdate(user);
    }

    @Override
    @Nonnull
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
