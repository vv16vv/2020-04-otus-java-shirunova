package ru.otus.vsh.hw12;

import org.hibernate.SessionFactory;
import ru.otus.vsh.hw12.dbCore.dao.RoleDao;
import ru.otus.vsh.hw12.dbCore.dao.UserDao;
import ru.otus.vsh.hw12.dbCore.dbService.DbServiceRoleImpl;
import ru.otus.vsh.hw12.dbCore.dbService.DbServiceUserImpl;
import ru.otus.vsh.hw12.dbCore.dbService.api.DBService;
import ru.otus.vsh.hw12.dbCore.model.Address;
import ru.otus.vsh.hw12.dbCore.model.Phone;
import ru.otus.vsh.hw12.dbCore.model.Role;
import ru.otus.vsh.hw12.dbCore.model.User;
import ru.otus.vsh.hw12.hibernate.HibernateUtils;
import ru.otus.vsh.hw12.hibernate.dao.RoleDaoHibernate;
import ru.otus.vsh.hw12.hibernate.dao.UserDaoHibernate;
import ru.otus.vsh.hw12.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw12.webCore.server.AdministrationWebServer;
import ru.otus.vsh.hw12.webCore.server.WebServer;
import ru.otus.vsh.hw12.webCore.services.TemplateProcessor;
import ru.otus.vsh.hw12.webCore.services.TemplateProcessorImpl;

import java.util.ArrayList;

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
        var roleUser = new Role(0,"user");
        var roleAdmin = new Role(0,"admin");
        var roleGuest = new Role(0,"guest");

        dbServiceRole.saveObject(roleUser);
        dbServiceRole.saveObject(roleAdmin);
        dbServiceRole.saveObject(roleGuest);

        DBService<User> dbServiceUser = new DbServiceUserImpl(userDao);

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
