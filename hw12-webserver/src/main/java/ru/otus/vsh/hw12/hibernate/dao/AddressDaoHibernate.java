package ru.otus.vsh.hw12.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw12.dbCore.dao.AddressDao;
import ru.otus.vsh.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw12.dbCore.model.Address;

public class AddressDaoHibernate extends AbstractDaoHibernate<Address> implements AddressDao {
    private final static Logger logger = LoggerFactory.getLogger(AddressDaoHibernate.class);

    public AddressDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
