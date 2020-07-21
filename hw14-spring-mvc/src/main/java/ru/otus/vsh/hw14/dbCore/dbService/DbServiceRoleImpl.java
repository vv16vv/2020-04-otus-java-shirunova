package ru.otus.vsh.hw14.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw14.dbCore.dao.RoleDao;
import ru.otus.vsh.hw14.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw14.dbCore.model.Role;

@Repository
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
