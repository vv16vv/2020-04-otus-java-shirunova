package ru.otus.vsh.hw12.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw12.dbCore.dao.AddressDao;
import ru.otus.vsh.hw12.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw12.dbCore.model.Address;

public class DbServiceAddressImpl extends AbstractDbServiceImpl<Address> implements DBServiceAddress {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceAddressImpl.class);

    public DbServiceAddressImpl(AddressDao addressDao) {
        super(addressDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
