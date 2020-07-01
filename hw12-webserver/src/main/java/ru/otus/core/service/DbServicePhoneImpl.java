package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.PhoneDao;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServicePhoneImpl implements DBServicePhone {
    private static final Logger logger = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    private final PhoneDao phoneDao;

    public DbServicePhoneImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public long newPhone(PhoneDataSet phone) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long phoneId = phoneDao.insertPhone(phone);
                sessionManager.commitSession();

                logger.info("created phone: {}", phoneId);
                return phoneId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void editPhone(PhoneDataSet phone) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                phoneDao.updatePhone(phone);
                sessionManager.commitSession();

                logger.info("edited phone: {}", phone.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }

    }

    @Override
    public long savePhone(PhoneDataSet phone) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                phoneDao.insertOrUpdate(phone);
                long phoneId = phone.getId();
                sessionManager.commitSession();

                logger.info("created or edited phone: {}", phoneId);
                return phoneId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<PhoneDataSet> getPhone(long id) {
        try (SessionManager sessionManager = phoneDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<PhoneDataSet> phoneOptional = phoneDao.findById(id);

                logger.info("phone: {}", phoneOptional.orElse(null));
                return phoneOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
