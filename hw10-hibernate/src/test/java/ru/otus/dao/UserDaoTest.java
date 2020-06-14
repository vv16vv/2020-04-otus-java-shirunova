package ru.otus.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.PhoneDaoHibernate;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManagerHibernate;
    private UserDaoHibernate userDaoHibernate;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                PhoneDataSet.class,
                AddressDataSet.class,
                User.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    void searchUnexistingUser() {
        sessionManagerHibernate.beginSession();
        Optional<User> user = userDaoHibernate.findById(100500);
        sessionManagerHibernate.commitSession();

        assertThat(user).isEmpty();
    }

    @Test
    void createNewUserInsert() {
        var address = new AddressDataSet(0, "Avia");
        var phones = new ArrayList<PhoneDataSet>();
        phones.add(new PhoneDataSet(0, "+71234567890"));
        phones.add(new PhoneDataSet(1, "+70987654321"));
        var user = new User(0L, "Alice", address, phones);

        sessionManagerHibernate.beginSession();
        var newUserId = userDaoHibernate.insertUser(user);
        sessionManagerHibernate.commitSession();

        logger.info("Added user {}", user);
        assertThat(newUserId).isGreaterThan(0L);
    }

    @Test
    void createNewUserInsertOrUpdate() {
        var address = new AddressDataSet(0, "Avia");
        var phones = new ArrayList<PhoneDataSet>();
        phones.add(new PhoneDataSet(0, "+71234567890"));
        phones.add(new PhoneDataSet(1, "+70987654321"));
        var user = new User(0L, "Alice", address, phones);

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(user);
        var newUserId = user.getId();
        sessionManagerHibernate.commitSession();

        assertThat(newUserId).isGreaterThan(0L);
    }

    @Test
    void searchExistingUser() {
        var address = new AddressDataSet(0, "Avia");
        var phones = new ArrayList<PhoneDataSet>();
        phones.add(new PhoneDataSet(0, "+71234567890"));
        phones.add(new PhoneDataSet(1, "+70987654321"));
        var user = new User(0L, "Alice", address, phones);

        sessionManagerHibernate.beginSession();
        var newUserId = userDaoHibernate.insertUser(user);
        sessionManagerHibernate.commitSession();
        logger.info("User is created with id #{}", newUserId);

        var phoneDaoHibernate = new PhoneDaoHibernate(sessionManagerHibernate);
        sessionManagerHibernate.beginSession();
        try {
            var foundPhone = phoneDaoHibernate.findById(phones.get(0).getId());

            var foundUser = userDaoHibernate.findById(newUserId);

            assertThat(foundUser).isPresent();

            logger.info("Found User is {}", foundUser.get());
            assertThat(foundUser.get()).isEqualTo(user);
        } finally {
            sessionManagerHibernate.commitSession();
        }
    }

//    @Test
//    void editUserUpdate() {
//        var user = new User(100500L, "+71234567890");
//        sessionManagerHibernate.beginSession();
//        userDaoHibernate.insertUser(user);
//        sessionManagerHibernate.commitSession();
//
//        user.setNumber("+70987654321");
//        sessionManagerHibernate.beginSession();
//        userDaoHibernate.updateUser(user);
//        sessionManagerHibernate.commitSession();
//
//        sessionManagerHibernate.beginSession();
//        var editedUser = userDaoHibernate.findById(user.getId());
//        sessionManagerHibernate.commitSession();
//
//        assertThat(editedUser).isPresent();
//        assertThat(editedUser.get().getNumber()).isEqualTo(user.getNumber());
//    }
//
//    @Test
//    void editUserInsertOrUpdate() {
//        var user = new User(100500L, "+71234567890");
//        sessionManagerHibernate.beginSession();
//        userDaoHibernate.insertUser(user);
//        sessionManagerHibernate.commitSession();
//
//        user.setNumber("+70987654321");
//        sessionManagerHibernate.beginSession();
//        userDaoHibernate.insertOrUpdate(user);
//        sessionManagerHibernate.commitSession();
//
//        sessionManagerHibernate.beginSession();
//        var editedUser = userDaoHibernate.findById(user.getId());
//        sessionManagerHibernate.commitSession();
//
//        assertThat(editedUser).isPresent();
//        assertThat(editedUser.get().getNumber()).isEqualTo(user.getNumber());
//    }
//

}
