package ru.otus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class DbExecutorTest {
    private static final Logger logger = LoggerFactory.getLogger(DbExecutorTest.class);
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

    @BeforeEach
    void init(){
        dataSource = new DataSourceH2();
        var sessionManager = new SessionManagerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl<User>();
        var userDao = UserDaoJdbc.initialize(sessionManager,dbExecutor);
        dbServiceUser = new DbServiceUserImpl(userDao);
        try {
            createTable(dataSource);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @AfterEach
    void clearUp(){
        try {
            deleteTable(dataSource);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    void searchUnexistingUser(){
        Optional<User> user = dbServiceUser.getUser(0);
        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    void newUserInsert(){
        var user = new User(100500, "May Sue", 33);
        var id = dbServiceUser.newUser(user);
        Assertions.assertEquals(1L, id);
    }

    @Test
    void newUserSave(){
        var user = new User(100500, "May Sue", 33);
        dbServiceUser.saveUser(user);
        Assertions.assertEquals(1L, user.getId());
    }

    @Test
    void searchForExistingUser(){
        var user = new User(100500, "May Sue", 33);
        var id = dbServiceUser.newUser(user);
        var newUser = dbServiceUser.getUser(id);
        Assertions.assertTrue(newUser.isPresent());
        Assertions.assertEquals(user, newUser.get());
    }

    @Test
    void editUserUpdate(){
        var user = new User(100500, "May Sue", 33);
        var id = dbServiceUser.newUser(user);
        user.setAge(18);
        dbServiceUser.editUser(user);
        var newUser = dbServiceUser.getUser(id);
        Assertions.assertTrue(newUser.isPresent());
        Assertions.assertEquals(user.getAge(), newUser.get().getAge());
    }

    @Test
    void editUserSave(){
        var user = new User(100500, "May Sue", 33);
        var id = dbServiceUser.newUser(user);
        user.setAge(18);
        dbServiceUser.saveUser(user);
        var newUser = dbServiceUser.getUser(id);
        Assertions.assertTrue(newUser.isPresent());
        Assertions.assertEquals(user.getAge(), newUser.get().getAge());
    }
}
