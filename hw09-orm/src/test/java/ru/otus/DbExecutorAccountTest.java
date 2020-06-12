package ru.otus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.Account;
import ru.otus.core.service.DBServiceAccount;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.AccountDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class DbExecutorAccountTest {
    private static final Logger logger = LoggerFactory.getLogger(DbExecutorAccountTest.class);
    private DataSource dataSource;
    private DBServiceAccount dbServiceAccount;

    private void createTable(@Nonnull DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        logger.info("table ACCOUNT created");
    }

    private void deleteTable(@Nonnull DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("drop table account")) {
            pst.executeUpdate();
        }
        logger.info("table ACCOUNT deleted");
    }

    @BeforeEach
    void init() {
        dataSource = new DataSourceH2();
        var sessionManager = new SessionManagerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl<Account>();
        var accountDao = AccountDaoJdbc.initialize(sessionManager, dbExecutor);
        dbServiceAccount = new DbServiceAccountImpl(accountDao);
        try {
            createTable(dataSource);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @AfterEach
    void clearUp() {
        try {
            deleteTable(dataSource);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void searchUnexistingAccount() {
        Optional<Account> account = dbServiceAccount.getAccount(0);
        Assertions.assertTrue(account.isEmpty());
    }

    @Test
    void newAccountInsert() {
        var account = new Account(100500, "Goods", 3.3f);
        var no = dbServiceAccount.newAccount(account);
        Assertions.assertEquals(1L, no);
    }

    @Test
    void newAccountSave() {
        var account = new Account(100500, "Goods", 3.3f);
        dbServiceAccount.saveAccount(account);
        Assertions.assertEquals(1L, account.getNo());
    }

    @Test
    void searchForExistingAccount() {
        var account = new Account(100500, "Goods", 3.3f);
        var no = dbServiceAccount.newAccount(account);
        var newAccount = dbServiceAccount.getAccount(no);
        Assertions.assertTrue(newAccount.isPresent());
        Assertions.assertEquals(account, newAccount.get());
    }

    @Test
    void editAccountUpdate() {
        var account = new Account(100500, "Goods", 3.3f);
        var no = dbServiceAccount.newAccount(account);
        account.setRest(1.8f);
        account.setType("Food");
        dbServiceAccount.editAccount(account);
        var newAccount = dbServiceAccount.getAccount(no);
        Assertions.assertTrue(newAccount.isPresent());
        Assertions.assertEquals(account, newAccount.get());
    }

    @Test
    void editAccountSave() {
        var account = new Account(100500, "Goods", 3.3f);
        var id = dbServiceAccount.newAccount(account);
        account.setRest(1.8f);
        account.setType("Food");
        dbServiceAccount.saveAccount(account);
        var newAccount = dbServiceAccount.getAccount(id);
        Assertions.assertTrue(newAccount.isPresent());
        Assertions.assertEquals(account, newAccount.get());
    }
}
