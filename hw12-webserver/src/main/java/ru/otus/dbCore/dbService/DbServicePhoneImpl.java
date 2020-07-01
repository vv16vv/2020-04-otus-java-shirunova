package ru.otus.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dbCore.dao.PhoneDao;
import ru.otus.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.dbCore.model.Phone;

public class DbServicePhoneImpl extends AbstractDbServiceImpl<Phone> {
    private static final Logger logger = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    public DbServicePhoneImpl(PhoneDao phoneDao) {
        super(phoneDao);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
