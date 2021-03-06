package ru.otus.vsh.hw14.dbCore.dbService;

import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw14.dbCore.dao.PhoneDao;
import ru.otus.vsh.hw14.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw14.dbCore.model.Phone;

@Repository
public class DbServicePhoneImpl extends AbstractDbServiceImpl<Phone> implements DBServicePhone {
    public DbServicePhoneImpl(PhoneDao phoneDao) {
        super(phoneDao);
    }
}
