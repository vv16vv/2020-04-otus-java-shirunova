package ru.otus.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.dao.RoleDao;
import ru.otus.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.dbCore.model.Role;

public class DbServiceRoleImpl extends AbstractDbServiceImpl<Role> {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceRoleImpl.class);

    public DbServiceRoleImpl(RoleDao roleDao) {
        super(roleDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
