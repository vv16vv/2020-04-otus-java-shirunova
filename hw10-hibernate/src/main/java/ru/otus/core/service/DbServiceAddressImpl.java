package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AddressDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAddressImpl implements DBServiceAddress {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceAddressImpl.class);

    private final AddressDao addressDao;

    public DbServiceAddressImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public long newAddress(AddressDataSet address) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long addressId = addressDao.insertAddress(address);
                sessionManager.commitSession();

                logger.info("created address: {}", addressId);
                return addressId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void editAddress(AddressDataSet address) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                addressDao.updateAddress(address);
                sessionManager.commitSession();

                logger.info("edited address: {}", address.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }

    }

    @Override
    public long saveAddress(AddressDataSet address) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                addressDao.insertOrUpdate(address);
                long addressId = address.getId();
                sessionManager.commitSession();

                logger.info("created or edited address: {}", addressId);
                return addressId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<AddressDataSet> getAddress(long id) {
        try (SessionManager sessionManager = addressDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<AddressDataSet> addressOptional = addressDao.findById(id);

                logger.info("address: {}", addressOptional.orElse(null));
                return addressOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
