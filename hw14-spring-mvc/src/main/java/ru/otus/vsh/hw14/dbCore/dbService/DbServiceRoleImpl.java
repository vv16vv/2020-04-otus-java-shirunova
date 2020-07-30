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
    private final RoleDao roleDao;

    public DbServiceRoleImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    @Override
    public Role findByName(String name) {
        return executeInSession((sm, roleName) -> {
            var role = roleDao.findByName(roleName);
            sm.commitSession();

            logger.info("found role with name = {}: {}", roleName, role);
            return role;
        }, name);
    }
}
