package ru.otus.vsh.hw16.dbCore.dbService;

import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw16.dbCore.dao.AddressDao;
import ru.otus.vsh.hw16.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw16.dbCore.model.Address;

@Repository
public class DbServiceAddressImpl extends AbstractDbServiceImpl<Address> implements DBServiceAddress {
    public DbServiceAddressImpl(AddressDao addressDao) {
        super(addressDao);
    }
}
