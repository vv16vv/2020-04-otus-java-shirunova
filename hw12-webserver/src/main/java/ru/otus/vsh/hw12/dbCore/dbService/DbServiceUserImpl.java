package ru.otus.vsh.hw12.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw12.dbCore.dao.UserDao;
import ru.otus.vsh.hw12.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw12.dbCore.model.User;

import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl extends AbstractDbServiceImpl<User> implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return executeInSession((sm, loginName) -> {
            var user = userDao.findByLogin(loginName);
            sm.commitSession();

            getLogger().info("found user with login = {}: {}", loginName, user);
            return user;
        }, login);
    }

    @Override
    public List<User> findAll() {
        return executeInSession((sm, notUsed) -> {
            var users = userDao.findAll();
            sm.commitSession();

            getLogger().info("found all users {}", users.toString());
            return users;
        }, "");
    }

    @Override
    public List<User> findByRole(String role) {
        return executeInSession((sm, roleName) -> {
            var users = userDao.findByRole(roleName);
            sm.commitSession();

            getLogger().info("found users with role = {}: {}", roleName, users);
            return users;
        }, role);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
