package ru.otus.core.service;

import ru.otus.core.model.Account;

import java.util.Optional;

public interface DBServiceAccount {

    void editAccount(Account account);

    long newAccount(Account account);

    void saveAccount(Account account);

    Optional<Account> getAccount(long no);
}
