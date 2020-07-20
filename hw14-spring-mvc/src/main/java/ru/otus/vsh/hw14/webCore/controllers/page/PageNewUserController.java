package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.model.Address;
import ru.otus.vsh.hw14.dbCore.model.Phone;
import ru.otus.vsh.hw14.dbCore.model.User;
import ru.otus.vsh.hw14.webCore.server.Routes;

import java.util.ArrayList;

@Controller
public class PageNewUserController {
    public static final String TEMPLATE_ROLES = "roles";
    public static final String TEMPLATE_ROUTE = "route";
    public static final String TEMPLATE_USER = "user";
    private static final String USER_PAGE_TEMPLATE = "new-user.html";
    private final DBServiceRole dbServiceRole;

    public PageNewUserController(DBServiceRole dbServiceRole) {
        this.dbServiceRole = dbServiceRole;
    }

    @GetMapping(Routes.PAGE_NEW_USER)
    public String getNewUserPage(Model model) {
        var address = new Address();
        var phone1 = new Phone();
        var phone2 = new Phone();
        var user = new User();
        user.setAddress(address);
        user.setPhones(new ArrayList<>());
        user.addPhone(phone1);
        user.addPhone(phone2);

        model.addAttribute(TEMPLATE_ROLES, dbServiceRole.findAll());
        model.addAttribute(TEMPLATE_ROUTE, Routes.API_USER);

        model.addAttribute(TEMPLATE_USER, user);
        return USER_PAGE_TEMPLATE;
    }

}
