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
import ru.otus.hibernate.dao.AddressDaoHibernate;
import ru.otus.hibernate.dao.PhoneDaoHibernate;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManagerHibernate;
    private UserDaoHibernate userDaoHibernate;
    private PhoneDaoHibernate phoneDaoHibernate;
    private AddressDaoHibernate addressDaoHibernate;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                PhoneDataSet.class,
                AddressDataSet.class,
                User.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
        phoneDaoHibernate = new PhoneDaoHibernate(sessionManagerHibernate);
        addressDaoHibernate = new AddressDaoHibernate(sessionManagerHibernate);
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
        var user = createTestUser();

        sessionManagerHibernate.beginSession();
        var newUserId = userDaoHibernate.insertUser(user);
        sessionManagerHibernate.commitSession();

        logger.info("Added user {}", user);
        assertThat(newUserId).isGreaterThan(0L);
    }

    @Test
    void createNewUserInsertOrUpdate() {
        var user = createTestUser();

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(user);
        var newUserId = user.getId();
        sessionManagerHibernate.commitSession();

        assertThat(newUserId).isGreaterThan(0L);
    }

    @Test
    void searchExistingUser() {
        var user = createTestUser();
        saveUserToDatabase(user);

        sessionManagerHibernate.beginSession();
        try {
            var foundAddress = addressDaoHibernate.findById(user.getAddress().getId());
            assertThat(foundAddress).isPresent();
            logger.info("Found address is {}", foundAddress.get());
            assertThat(foundAddress.get()).isEqualTo(user.getAddress());

            var expectedPhone1 = user.getPhones().get(0);
            var foundPhone1 = phoneDaoHibernate.findById(expectedPhone1.getId());
            assertThat(foundPhone1).isPresent();
            logger.info("Found phone #1 is {}", foundPhone1.get());
            assertThat(foundPhone1.get()).isEqualTo(expectedPhone1);

            var expectedPhone2 = user.getPhones().get(1);
            var foundPhone2 = phoneDaoHibernate.findById(expectedPhone2.getId());
            assertThat(foundPhone2).isPresent();
            logger.info("Found phone #2 is {}", foundPhone2.get());
            assertThat(foundPhone2.get()).isEqualTo(expectedPhone2);

            var foundUser = userDaoHibernate.findById(user.getId());

            assertThat(foundUser).isPresent();

            logger.info("Found User is {}", foundUser.get());
            assertThat(foundUser.get()).isEqualTo(user);
        } finally {
            sessionManagerHibernate.commitSession();
        }
    }

    @Test
    void editUserNameUpdate() {
        var user = createTestUser();
        saveUserToDatabase(user);

        user.setName("Bob");
        sessionManagerHibernate.beginSession();
        userDaoHibernate.updateUser(user);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        try {
            var editedUser = userDaoHibernate.findById(user.getId());

            assertThat(editedUser).isPresent();
            assertThat(editedUser.get()).isEqualTo(user);
        } finally {
            sessionManagerHibernate.commitSession();
        }
    }

    @Test
    void editUserAddressUpdate() {
        var user = createTestUser();
        saveUserToDatabase(user);

        user.getAddress().setStreet("221B, Baker Street");
        sessionManagerHibernate.beginSession();
        userDaoHibernate.updateUser(user);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        try {
            var editedUser = userDaoHibernate.findById(user.getId());

            assertThat(editedUser).isPresent();
            assertThat(editedUser.get()).isEqualTo(user);
        } finally {
            sessionManagerHibernate.commitSession();
        }
    }

    @Test
    void editUserPhoneUpdate() {
        var user = createTestUser();
        saveUserToDatabase(user);

        user.getPhones().get(0).setNumber("+12345678900");
        sessionManagerHibernate.beginSession();
        userDaoHibernate.updateUser(user);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        try {
            var editedUser = userDaoHibernate.findById(user.getId());

            assertThat(editedUser).isPresent();
            assertThat(editedUser.get()).isEqualTo(user);
        } finally {
            sessionManagerHibernate.commitSession();
        }
    }

    @Test
    void editUserNewPhoneUpdate() {
        var user = createTestUser();
        saveUserToDatabase(user);

        var newPhone = new PhoneDataSet(99, "+12345678900");
        user.addPhone(newPhone);
        sessionManagerHibernate.beginSession();
        phoneDaoHibernate.insertPhone(newPhone);
        userDaoHibernate.updateUser(user);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        try {
            var editedUser = userDaoHibernate.findById(user.getId());

            assertThat(editedUser).isPresent();
            assertThat(editedUser.get()).isEqualTo(user);
        } finally {
            sessionManagerHibernate.commitSession();
        }
    }

    @Test
    void editUserInsertOrUpdate() {
        var user = createTestUser();
        saveUserToDatabase(user);

        user.setName("Bob");
        user.getAddress().setStreet("221B, Baker Street");
        user.getPhones().get(0).setNumber("+12345678900");
        var newPhone = new PhoneDataSet(99, "+12345678900");
        user.addPhone(newPhone);

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(user);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        try {
            var editedUser = userDaoHibernate.findById(user.getId());

            assertThat(editedUser).isPresent();
            assertThat(editedUser.get()).isEqualTo(user);
        }
        finally {
            sessionManagerHibernate.commitSession();
        }
    }

    @Nonnull
    private User createTestUser() {
        var address = new AddressDataSet(0, "Avia");
        var phones = new ArrayList<PhoneDataSet>();
        var user = new User(0L, "Alice", address, phones);
        user.addPhone(new PhoneDataSet(0, "+71234567890"));
        user.addPhone(new PhoneDataSet(1, "+70987654321"));

        return user;
    }

    private void saveUserToDatabase(@Nonnull User user) {
        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertUser(user);
        sessionManagerHibernate.commitSession();
    }
}
