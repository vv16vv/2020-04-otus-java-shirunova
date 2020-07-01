package ru.otus.core.service;

import ru.otus.core.model.PhoneDataSet;

import java.util.Optional;

public interface DBServicePhone {

    long savePhone(PhoneDataSet phone);

    long newPhone(PhoneDataSet phone);

    void editPhone(PhoneDataSet phone);

    Optional<PhoneDataSet> getPhone(long id);

}
