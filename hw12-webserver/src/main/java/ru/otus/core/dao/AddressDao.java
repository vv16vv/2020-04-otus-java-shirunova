package ru.otus.core.dao;

import ru.otus.core.model.AddressDataSet;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface AddressDao {
    Optional<AddressDataSet> findById(long id);

    long insertAddress(AddressDataSet address);

    void updateAddress(AddressDataSet address);

    void insertOrUpdate(AddressDataSet address);

    SessionManager getSessionManager();
}
