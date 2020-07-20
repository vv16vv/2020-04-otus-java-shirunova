package ru.otus.vsh.hw14.dbCore.dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw14.dbCore.dao.UserDao;
import ru.otus.vsh.hw14.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw14.dbCore.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class DbServiceUserImpl extends AbstractDbServiceImpl<User> implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao, DBServiceRole dbServiceRole) {
        super(userDao);
        this.userDao = userDao;

//        var roles = dbServiceRole.findAll();
//        var roleAdmin = roles.stream().filter(role -> role.getName().equals("admin")).findFirst().get();
//        var roleGuest = roles.stream().filter(role -> role.getName().equals("guest")).findFirst().get();
//        var roleUser = roles.stream().filter(role -> role.getName().equals("user")).findFirst().get();
//
//        var address1 = new Address(0, "Планерная улица");
//        var address2 = new Address(0, "Проспект Авиаконструкторов");
//        var address3 = new Address(0, "Комендантский проспект");
//
//        var phone1 = new Phone(0, "+7(123)456-78-90");
//        var phone2 = new Phone(0, "+7(987)876-23-23");
//        var phone3 = new Phone(0, "+7(111)222-33-44");
//        var phone4 = new Phone(0, "+7(098)124-63-74");
//
//        var user1 = new User(0, "vitkus", "Виктория", "12345", address1, roleAdmin, new ArrayList<>());
//        user1.addPhone(phone1);
//        user1.addPhone(phone2);
//        var user2 = new User(0, "sevantius", "Всеволод", "11111", address2, roleGuest, new ArrayList<>());
//        user2.addPhone(phone3);
//        var user3 = new User(0, "koshir", "Константин", "24680", address3, roleUser, new ArrayList<>());
//        user3.addPhone(phone4);
//
//        saveObject(user1);
//        saveObject(user2);
//        saveObject(user3);

    }

    @Override
    public Optional<User> findByLogin(String login) {
        return executeInSession((sm, loginName) -> {
            var user = userDao.findByLogin(loginName);
            sm.commitSession();

            getLogger().info("found user with login = {}: {}", loginName, user);
            return user;
        }, login);
    }

    @Override
    public List<User> findByRole(String role) {
        return executeInSession((sm, roleName) -> {
            var users = userDao.findByRole(roleName);
            sm.commitSession();

            getLogger().info("found users with role = {}: {}", roleName, users);
            return users;
        }, role);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
