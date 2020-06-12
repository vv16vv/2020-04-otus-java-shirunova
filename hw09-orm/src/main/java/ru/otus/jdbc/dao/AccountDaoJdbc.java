package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.JdbcMapper;
import ru.otus.jdbc.mapper.impl.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManager sessionManager;
    private final JdbcMapper<Account> accountMapper;

    private AccountDaoJdbc(
            @Nonnull SessionManagerJdbc sessionManager,
            @Nonnull JdbcMapper<Account> accountMapper) {
        this.sessionManager = sessionManager;
        this.accountMapper = accountMapper;
    }

    @Nonnull
    public static AccountDao initialize(
            @Nonnull SessionManagerJdbc sessionManager,
            @Nonnull DbExecutorImpl<Account> dbExecutor) {
        return new AccountDaoJdbc(sessionManager, JdbcMapperImpl.initialize(Account.class, sessionManager, dbExecutor));
    }

    @Override
    @Nonnull
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(accountMapper.findById(id));
    }

    @Override
    public long insertAccount(@Nonnull Account account) {
        accountMapper.insert(account);
        return account.getNo();
    }

    @Override
    public void updateAccount(@Nonnull Account account) {
        accountMapper.update(account);
    }

    @Override
    public void insertOrUpdate(@Nonnull Account account) {
        accountMapper.insertOrUpdate(account);
    }

    @Override
    @Nonnull
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
