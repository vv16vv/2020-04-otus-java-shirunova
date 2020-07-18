package ru.otus.vsh.hw14.hibernate.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw14.dbCore.dao.PhoneDao;
import ru.otus.vsh.hw14.dbCore.model.Phone;
import ru.otus.vsh.hw14.hibernate.sessionmanager.SessionManagerHibernate;

public class PhoneDaoHibernate extends AbstractDaoHibernate<Phone> implements PhoneDao {
    private final static Logger logger = LoggerFactory.getLogger(PhoneDaoHibernate.class);

    public PhoneDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Phone.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
