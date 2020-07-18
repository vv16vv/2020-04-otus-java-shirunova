package ru.otus.vsh.hw14.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.vsh.hw14.dbCore.dao.PhoneDao;
import ru.otus.vsh.hw14.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw14.dbCore.model.Phone;

public class DbServicePhoneImpl extends AbstractDbServiceImpl<Phone> implements DBServicePhone {
    private static final Logger logger = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    public DbServicePhoneImpl(PhoneDao phoneDao) {
        super(phoneDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
