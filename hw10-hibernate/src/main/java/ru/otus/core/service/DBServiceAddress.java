package ru.otus.core.service;

import ru.otus.core.model.AddressDataSet;

import java.util.Optional;

public interface DBServiceAddress {

    long saveAddress(AddressDataSet address);

    long newAddress(AddressDataSet address);

    void editAddress(AddressDataSet address);

    Optional<AddressDataSet> getAddress(long id);

}
