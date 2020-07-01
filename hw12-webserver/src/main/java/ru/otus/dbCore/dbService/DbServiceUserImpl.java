package ru.otus.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.dao.UserDao;
import ru.otus.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.dbCore.model.User;

public class DbServiceUserImpl extends AbstractDbServiceImpl<User> {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    public DbServiceUserImpl(UserDao userDao) {
        super(userDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
