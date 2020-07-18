package ru.otus.vsh.hw14.hibernate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw14.dbCore.dao.AddressDao;
import ru.otus.vsh.hw14.dbCore.model.Address;
import ru.otus.vsh.hw14.hibernate.sessionmanager.SessionManagerHibernate;

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
