package ru.otus.vsh.hw14.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw14.dbCore.dao.AddressDao;
import ru.otus.vsh.hw14.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw14.dbCore.model.Address;

@Repository
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
