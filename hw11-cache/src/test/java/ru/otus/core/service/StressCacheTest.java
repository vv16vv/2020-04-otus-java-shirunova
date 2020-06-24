package ru.otus.core.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.model.User;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.SQLException;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class StressCacheTest {
    private static final Logger logger = LoggerFactory.getLogger(StressCacheTest.class);
    private final int n = 10000;
    private DataSource dataSource;
    private DBServiceUser dbServiceUser;

    private void createTable(@Nonnull DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        logger.info("table USER created");
    }

    private void deleteTable(@Nonnull DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("drop table user")) {
            pst.executeUpdate();
        }
        logger.info("table USER deleted");
    }

    private void init(@Nullable MyCache<String, User> cache) {
        dataSource = new DataSourceH2();
        var sessionManager = new SessionManagerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl<User>();
        var userDao = UserDaoJdbc.create(sessionManager, dbExecutor);
        dbServiceUser = new DbServiceUserImpl(userDao, cache);
        try {
            createTable(dataSource);
            logger.info("Add {} users", n);
            for (int i = 0; i < n; i++) {
                var user = new User(i, String.format("May Sue #%d", i + 1), i);
                dbServiceUser.newUser(user);
            }
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

    /**
     * time: 16s 318ms
     */
    @Test
    void readManyUsers() {
        init(null);
        logger.info("Read {} users", n);
        for (int i = 0; i < n; i++) {
            var newUser = dbServiceUser.getUser(i + 1);
            System.out.println("newUser = " + newUser);
        }
    }

    /**
     * time: 12s 546ms
     */
    @Test
    void readManyCachedUsers() {
        init(new MyCache<>());
        logger.info("Read {} cached users", n);
        for (int i = 0; i < n; i++) {
            var newUser = dbServiceUser.getUser(i + 1);
            System.out.println("newUser = " + newUser);
        }
    }

}
