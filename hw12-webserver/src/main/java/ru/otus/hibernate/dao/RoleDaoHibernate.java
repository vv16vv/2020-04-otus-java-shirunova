package ru.otus.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.model.Role;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

public class RoleDaoHibernate extends AbstractDaoHibernate<Role> {
    private final static Logger logger = LoggerFactory.getLogger(RoleDaoHibernate.class);

    public RoleDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Role.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
