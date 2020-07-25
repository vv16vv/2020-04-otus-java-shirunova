package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.webCore.controllers.dataClasses.UserData;
import ru.otus.vsh.hw14.webCore.server.Routes;

@Controller
public class PageNewUserController {
    private static final String TEMPLATE_ROLES = "roles";
    private static final String TEMPLATE_ROUTE = "route";
    private static final String TEMPLATE_USER = "user";
    private static final String USER_PAGE_TEMPLATE = "new-user.html";
    private final DBServiceRole dbServiceRole;

    public PageNewUserController(DBServiceRole dbServiceRole) {
        this.dbServiceRole = dbServiceRole;
    }

    @GetMapping(Routes.PAGE_NEW_USER)
    public String getNewUserPage(Model model) {
        model.addAttribute(TEMPLATE_ROLES, dbServiceRole.findAll());
        model.addAttribute(TEMPLATE_ROUTE, Routes.API_USER);

        model.addAttribute(TEMPLATE_USER, new UserData());
        return USER_PAGE_TEMPLATE;
    }

}
