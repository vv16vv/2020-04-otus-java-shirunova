package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.dbCore.model.Address;
import ru.otus.vsh.hw14.dbCore.model.Phone;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.dbCore.model.User;
import ru.otus.vsh.hw14.webCore.controllers.dataClasses.UserData;
import ru.otus.vsh.hw14.webCore.server.Routes;

import java.util.ArrayList;

@Controller
public class PageNewUserController {
    private static final String TEMPLATE_ROLES = "roles";
    private static final String TEMPLATE_ROUTE = "route";
    private static final String TEMPLATE_USER = "user";
    private static final String USER_PAGE_TEMPLATE = "new-user.html";
    private final DBServiceRole dbServiceRole;
    private final DBServiceUser dbServiceUser;

    public PageNewUserController(DBServiceRole dbServiceRole, DBServiceUser dbServiceUser) {
        this.dbServiceRole = dbServiceRole;
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping(Routes.PAGE_NEW_USER)
    public String getNewUserPage(Model model) {
        model.addAttribute(TEMPLATE_ROLES, dbServiceRole.findAll());
        model.addAttribute(TEMPLATE_ROUTE, Routes.PAGE_NEW_USER);

        model.addAttribute(TEMPLATE_USER, new UserData());
        return USER_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.PAGE_NEW_USER)
    public RedirectView addUser(@ModelAttribute UserData userData) {
        var role = dbServiceRole.findByName(userData.getRole());
        if (role == null) {
            role = new Role(userData.getRole());
            dbServiceRole.saveObject(role);
        }
        var address = new Address(userData.getAddress());
        var user = new User(
                userData.getLogin(),
                userData.getName(),
                userData.getPassword(),
                address,
                role,
                new ArrayList<>());
        user.addPhone(new Phone(userData.getPhone1()));
        var phone2 = userData.getPhone2();
        if (!phone2.isEmpty()) user.addPhone(new Phone(phone2));
        dbServiceUser.saveObject(user);
        return new RedirectView(Routes.PAGE_USERS, true);
    }

}
