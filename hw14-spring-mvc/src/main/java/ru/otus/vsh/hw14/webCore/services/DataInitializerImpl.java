package ru.otus.vsh.hw14.webCore.services;

import org.springframework.stereotype.Component;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.dbCore.model.Address;
import ru.otus.vsh.hw14.dbCore.model.Phone;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.dbCore.model.User;

import java.util.ArrayList;

@Component
public class DataInitializerImpl implements DataInitializer {
    private final DBServiceRole dbServiceRole;
    private final DBServiceUser dbServiceUser;

    public DataInitializerImpl(DBServiceRole dbServiceRole, DBServiceUser dbServiceUser) {
        this.dbServiceRole = dbServiceRole;
        this.dbServiceUser = dbServiceUser;

        createInitialData();
    }

    @Override
    public void createInitialData() {
        var roleUser = new Role(0, "user");
        var roleAdmin = new Role(0, "admin");
        var roleGuest = new Role(0, "guest");

        dbServiceRole.saveObject(roleUser);
        dbServiceRole.saveObject(roleAdmin);
        dbServiceRole.saveObject(roleGuest);

        var address1 = new Address(0, "Планерная улица");
        var address2 = new Address(0, "Проспект Авиаконструкторов");
        var address3 = new Address(0, "Комендантский проспект");

        var phone1 = new Phone(0, "+7(123)456-78-90");
        var phone2 = new Phone(0, "+7(987)876-23-23");
        var phone3 = new Phone(0, "+7(111)222-33-44");
        var phone4 = new Phone(0, "+7(098)124-63-74");

        var user1 = new User(0, "vitkus", "Виктория", "12345", address1, roleAdmin, new ArrayList<>());
        user1.addPhone(phone1);
        user1.addPhone(phone2);
        var user2 = new User(0, "sevantius", "Всеволод", "11111", address2, roleGuest, new ArrayList<>());
        user2.addPhone(phone3);
        var user3 = new User(0, "koshir", "Константин", "24680", address3, roleUser, new ArrayList<>());
        user3.addPhone(phone4);

        dbServiceUser.saveObject(user1);
        dbServiceUser.saveObject(user2);
        dbServiceUser.saveObject(user3);

    }
}
