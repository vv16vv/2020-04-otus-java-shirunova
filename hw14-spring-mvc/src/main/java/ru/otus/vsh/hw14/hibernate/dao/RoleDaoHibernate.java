package ru.otus.vsh.hw14.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw14.dbCore.dao.RoleDao;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.hibernate.sessionmanager.SessionManagerHibernate;

@Component
public class RoleDaoHibernate extends AbstractDaoHibernate<Role> implements RoleDao {
    private final static Logger logger = LoggerFactory.getLogger(RoleDaoHibernate.class);

    public RoleDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Role.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public Role findByName(String name) {
        try {
            var entityManager = sessionManager.getEntityManager();
            return entityManager
                    .createNamedQuery(Role.GET_ROLE_BY_NAME, Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
        }
        return null;
    }
}
