package ru.otus.core.dao;

import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface PhoneDao {
    Optional<PhoneDataSet> findById(long id);

    long insertPhone(PhoneDataSet phone);

    void updatePhone(PhoneDataSet phone);

    void insertOrUpdate(PhoneDataSet phone);

    SessionManager getSessionManager();
}
