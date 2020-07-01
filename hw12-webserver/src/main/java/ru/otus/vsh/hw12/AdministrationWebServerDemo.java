package ru.otus;

import com.google.common.collect.Lists;
import org.hibernate.SessionFactory;
import ru.otus.dbCore.dao.UserDao;
import ru.otus.dbCore.dbService.DBServiceUser;
import ru.otus.dbCore.dbService.DbServiceUserImpl;
import ru.otus.dbCore.dbService.api.DBService;
import ru.otus.dbCore.model.Address;
import ru.otus.dbCore.model.Phone;
import ru.otus.dbCore.model.Role;
import ru.otus.dbCore.model.User;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.webCore.server.AdministrationWebServer;
import ru.otus.webCore.server.WebServer;
import ru.otus.webCore.services.TemplateProcessor;
import ru.otus.webCore.services.TemplateProcessorImpl;

import java.util.ArrayList;
import java.util.Optional;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class AdministrationWebServerDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class, Role.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        RoleDao roleDao = new RoleDaoHibernate(sessionManager);
        initiateData(userDao, roleDao);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        WebServer usersWebServer = new AdministrationWebServer(WEB_SERVER_PORT, userDao, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void initiateData(UserDao userDao, RoleDao roleDao){
        DBService<Role> dbServiceRole = new DbServiceRoleImpl(roleDao);
        var role1 = new Role(1,"user");
        var role2 = new Role(2,"admin");
        var role3 = new Role(3,"guest");

        dbServiceRole.saveObject(role1);
        dbServiceRole.saveObject(role2);
        dbServiceRole.saveObject(role3);

        DBService<User> dbServiceUser = new DbServiceUserImpl(userDao);

        var address1 = new Address(1, "Планерная улица");
        var address2 = new Address(2, "Проспект Авиаконструкторов");
        var address3 = new Address(3, "Комендантский проспект");

        var phone1 = new Phone(1, "+7(123)456-78-90");
        var phone2 = new Phone(2, "+7(987)876-23-23");
        var phone3 = new Phone(3, "+7(111)222-33-44");
        var phone4 = new Phone(4, "+7(098)124-63-74");

        var user1 = new User(1, "vitkus", "Виктория", "12345", address1, role2, new ArrayList<>());
        user1.addPhone(phone1);
        user1.addPhone(phone2);
        var user2 = new User(2, "sevantius", "Всеволод", "32321", address2, role3, new ArrayList<>());
        user2.addPhone(phone3);
        var user3 = new User(3, "koshir", "Константин", "91929", address3, role1, new ArrayList<>());
        user3.addPhone(phone4);

        dbServiceUser.saveObject(user1);
        dbServiceUser.saveObject(user2);
        dbServiceUser.saveObject(user3);
    }
}
