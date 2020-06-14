package ru.otus.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.PhoneDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(PhoneDaoTest.class);
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManagerHibernate;
    private PhoneDaoHibernate phoneDaoHibernate;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, PhoneDataSet.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        phoneDaoHibernate = new PhoneDaoHibernate(sessionManagerHibernate);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    void searchUnexistingPhone() {
        sessionManagerHibernate.beginSession();
        Optional<PhoneDataSet> phone = phoneDaoHibernate.findById(100500);
        sessionManagerHibernate.commitSession();

        assertThat(phone).isEmpty();
    }

    @Test
    void createNewPhoneInsert() {
        var phone = new PhoneDataSet(100500L, "+71234567890");
        sessionManagerHibernate.beginSession();
        var newPhoneId = phoneDaoHibernate.insertPhone(phone);
        sessionManagerHibernate.commitSession();
        assertThat(newPhoneId).isGreaterThan(0L);
    }

    @Test
    void createNewPhoneInsertOrUpdate() {
        var phone = new PhoneDataSet(100500L, "+71234567890");
        sessionManagerHibernate.beginSession();
        phoneDaoHibernate.insertOrUpdate(phone);
        var newPhoneId = phone.getId();
        sessionManagerHibernate.commitSession();
        assertThat(newPhoneId).isGreaterThan(0L);
    }

    @Test
    void searchExistingPhone() {
        var phone = new PhoneDataSet(100500L, "+71234567890");
        sessionManagerHibernate.beginSession();
        var newPhoneId = phoneDaoHibernate.insertPhone(phone);
        sessionManagerHibernate.commitSession();
        logger.info("Phone is created with id #{}",newPhoneId);

        sessionManagerHibernate.beginSession();
        var foundPhone = phoneDaoHibernate.findById(newPhoneId);
        sessionManagerHibernate.commitSession();
        logger.info("Found phone is {}",foundPhone);

        assertThat(foundPhone).isPresent();
        assertThat(foundPhone.get()).isEqualTo(phone);
    }

    @Test
    void editPhoneUpdate() {
        var phone = new PhoneDataSet(100500L, "+71234567890");
        sessionManagerHibernate.beginSession();
        phoneDaoHibernate.insertPhone(phone);
        sessionManagerHibernate.commitSession();

        phone.setNumber("+70987654321");
        sessionManagerHibernate.beginSession();
        phoneDaoHibernate.updatePhone(phone);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        var editedPhone = phoneDaoHibernate.findById(phone.getId());
        sessionManagerHibernate.commitSession();

        assertThat(editedPhone).isPresent();
        assertThat(editedPhone.get().getNumber()).isEqualTo(phone.getNumber());
    }

    @Test
    void editPhoneInsertOrUpdate() {
        var phone = new PhoneDataSet(100500L, "+71234567890");
        sessionManagerHibernate.beginSession();
        phoneDaoHibernate.insertPhone(phone);
        sessionManagerHibernate.commitSession();

        phone.setNumber("+70987654321");
        sessionManagerHibernate.beginSession();
        phoneDaoHibernate.insertOrUpdate(phone);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        var editedPhone = phoneDaoHibernate.findById(phone.getId());
        sessionManagerHibernate.commitSession();

        assertThat(editedPhone).isPresent();
        assertThat(editedPhone.get().getNumber()).isEqualTo(phone.getNumber());
    }


}
