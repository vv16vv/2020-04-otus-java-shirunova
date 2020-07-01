package ru.otus.vsh.hw12.hibernate.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw12.dbCore.model.Phone;
import ru.otus.vsh.hw12.hibernate.sessionmanager.SessionManagerHibernate;

public class PhoneDaoHibernate extends AbstractDaoHibernate<Phone> {
    private final static Logger logger = LoggerFactory.getLogger(PhoneDaoHibernate.class);

    public PhoneDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Phone.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
