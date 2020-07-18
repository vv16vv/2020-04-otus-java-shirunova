package ru.otus.vsh.hw14.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw14.dbCore.dao.RoleDao;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.hibernate.sessionmanager.SessionManagerHibernate;

public class RoleDaoHibernate extends AbstractDaoHibernate<Role> implements RoleDao {
    private final static Logger logger = LoggerFactory.getLogger(RoleDaoHibernate.class);

    public RoleDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Role.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
