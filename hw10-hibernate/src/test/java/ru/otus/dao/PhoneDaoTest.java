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
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, PhoneDataSet.class, User.class, AddressDataSet.class);
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

}
