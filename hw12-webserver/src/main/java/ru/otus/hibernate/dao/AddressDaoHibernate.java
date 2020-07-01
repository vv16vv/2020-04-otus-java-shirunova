package ru.otus.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.model.Address;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

public class AddressDaoHibernate extends AbstractDaoHibernate<Address> {
    private final static Logger logger = LoggerFactory.getLogger(AddressDaoHibernate.class);

    public AddressDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
