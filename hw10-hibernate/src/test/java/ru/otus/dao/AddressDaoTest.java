package ru.otus.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.AddressDataSet;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.AddressDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(AddressDaoTest.class);
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManagerHibernate;
    private AddressDaoHibernate addressDaoHibernate;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, AddressDataSet.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        addressDaoHibernate = new AddressDaoHibernate(sessionManagerHibernate);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    void searchUnexistingAddress() {
        sessionManagerHibernate.beginSession();
        Optional<AddressDataSet> address = addressDaoHibernate.findById(100500);
        sessionManagerHibernate.commitSession();

        assertThat(address).isEmpty();
    }

    @Test
    void createNewAddressInsert() {
        var address = new AddressDataSet(100500L, "221b, Baker street");
        sessionManagerHibernate.beginSession();
        var newAddressId = addressDaoHibernate.insertAddress(address);
        sessionManagerHibernate.commitSession();
        assertThat(newAddressId).isGreaterThan(0L);
    }

    @Test
    void createNewAddressInsertOrUpdate() {
        var address = new AddressDataSet(100500L, "221b, Baker street");
        sessionManagerHibernate.beginSession();
        addressDaoHibernate.insertOrUpdate(address);
        var newAddressId = address.getId();
        sessionManagerHibernate.commitSession();
        assertThat(newAddressId).isGreaterThan(0L);
    }

    @Test
    void searchExistingAddress() {
        var address = new AddressDataSet(100500L, "221b, Baker street");
        sessionManagerHibernate.beginSession();
        var newAddressId = addressDaoHibernate.insertAddress(address);
        sessionManagerHibernate.commitSession();
        logger.info("Address is created with id #{}", newAddressId);

        sessionManagerHibernate.beginSession();
        var foundAddress = addressDaoHibernate.findById(newAddressId);
        sessionManagerHibernate.commitSession();
        logger.info("Found address is {}", foundAddress);

        assertThat(foundAddress).isPresent();
        assertThat(foundAddress.get()).isEqualTo(address);
    }

    @Test
    void editAddressUpdate() {
        var address = new AddressDataSet(100500L, "221b, Baker street");
        sessionManagerHibernate.beginSession();
        addressDaoHibernate.insertAddress(address);
        sessionManagerHibernate.commitSession();

        address.setStreet("32, Sheridan Rd");
        sessionManagerHibernate.beginSession();
        addressDaoHibernate.updateAddress(address);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        var editedAddress = addressDaoHibernate.findById(address.getId());
        sessionManagerHibernate.commitSession();

        assertThat(editedAddress).isPresent();
        assertThat(editedAddress.get().getStreet()).isEqualTo(address.getStreet());
    }

    @Test
    void editAddressInsertOrUpdate() {
        var address = new AddressDataSet(100500L, "221b, Baker street");
        sessionManagerHibernate.beginSession();
        addressDaoHibernate.insertAddress(address);
        sessionManagerHibernate.commitSession();

        address.setStreet("32, Sheridan Rd");
        sessionManagerHibernate.beginSession();
        addressDaoHibernate.insertOrUpdate(address);
        sessionManagerHibernate.commitSession();

        sessionManagerHibernate.beginSession();
        var editedAddress = addressDaoHibernate.findById(address.getId());
        sessionManagerHibernate.commitSession();

        assertThat(editedAddress).isPresent();
        assertThat(editedAddress.get().getStreet()).isEqualTo(address.getStreet());
    }


}
