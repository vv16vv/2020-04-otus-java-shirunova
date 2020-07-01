package ru.otus.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.dao.AddressDao;
import ru.otus.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.dbCore.model.Address;

public class DbServiceAddressImpl extends AbstractDbServiceImpl<Address> {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceAddressImpl.class);

    public DbServiceAddressImpl(AddressDao addressDao) {
        super(addressDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
