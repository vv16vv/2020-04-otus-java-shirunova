package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.UserDaoJdbc;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author sergey
 * created on 03.02.19.
 */
@SuppressWarnings("SqlNoDataSourceInspection")
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        var userDao = UserDaoJdbc.initialize(sessionManager, dbExecutor);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 55));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(
                crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created")
        );

        if(user.isPresent()) {
            user.ifPresent(demo::changeUser);
            var id1 = dbServiceUser.saveUser(user.get());
            Optional<User> user2 = dbServiceUser.getUser(id1);

            user2.ifPresentOrElse(
                    crUser -> logger.info("update user, name:{}", crUser.getName()),
                    () -> logger.info("user was not found")
            );
        }
    }

    private void changeUser(@Nonnull User user){
        user.setName("New name");
        user.setAge(33);
    }

    private void createTable(@Nonnull DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
