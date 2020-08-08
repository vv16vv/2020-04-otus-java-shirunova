package ru.otus.vsh.hw12.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw12.dbCore.dao.RoleDao;
import ru.otus.vsh.hw12.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw12.dbCore.model.Role;

public class DbServiceRoleImpl extends AbstractDbServiceImpl<Role> implements DBServiceRole {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceRoleImpl.class);

    public DbServiceRoleImpl(RoleDao roleDao) {
        super(roleDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
