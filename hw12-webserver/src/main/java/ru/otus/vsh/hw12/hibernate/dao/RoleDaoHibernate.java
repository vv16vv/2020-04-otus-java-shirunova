package ru.otus.vsh.hw12.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw12.dbCore.dao.RoleDao;
import ru.otus.vsh.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw12.dbCore.model.Role;

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
