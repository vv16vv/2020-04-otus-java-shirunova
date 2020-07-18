package ru.otus.vsh.hw14;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import ru.otus.vsh.hw14.dbCore.dao.RoleDao;
import ru.otus.vsh.hw14.dbCore.dao.UserDao;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.dbCore.dbService.DbServiceRoleImpl;
import ru.otus.vsh.hw14.dbCore.dbService.DbServiceUserImpl;
import ru.otus.vsh.hw14.dbCore.model.Address;
import ru.otus.vsh.hw14.dbCore.model.Phone;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.dbCore.model.User;
import ru.otus.vsh.hw14.gson.PhoneJsonSerializer;
import ru.otus.vsh.hw14.hibernate.HibernateUtils;
import ru.otus.vsh.hw14.hibernate.dao.RoleDaoHibernate;
import ru.otus.vsh.hw14.hibernate.dao.UserDaoHibernate;
import ru.otus.vsh.hw14.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw14.webCore.server.AdministrationWebServer;
import ru.otus.vsh.hw14.webCore.server.WebServer;
import ru.otus.vsh.hw14.webCore.services.TemplateProcessor;
import ru.otus.vsh.hw14.webCore.services.TemplateProcessorImpl;
import ru.otus.vsh.hw14.webCore.services.UserAuthServiceImpl;

import java.util.ArrayList;

/*
    // Стартовая страница
    http://localhost:8080

*/
public class AdministrationWebServerDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class, Role.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        RoleDao roleDao = new RoleDaoHibernate(sessionManager);
        DBServiceRole dbServiceRole = new DbServiceRoleImpl(roleDao);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        initiateData(dbServiceUser, dbServiceRole);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(Phone.class, new PhoneJsonSerializer());
        Gson gson = builder.create();

        WebServer usersWebServer = new AdministrationWebServer(WEB_SERVER_PORT,
                dbServiceUser,
                dbServiceRole,
                new UserAuthServiceImpl(dbServiceUser),
                templateProcessor,
                gson);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void initiateData(DBServiceUser dbServiceUser, DBServiceRole dbServiceRole) {
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
