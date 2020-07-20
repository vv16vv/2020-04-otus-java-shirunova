package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.webCore.server.Routes;

@Controller
public class PageUsersController {

    private static final String TEMPLATE_USERS = "users";
    private static final String TEMPLATE_ACTIONS = "actions";
    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final DBServiceUser dbServiceUser;

    public PageUsersController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping(Routes.PAGE_USERS)
    public String getUsersPage(Model model) {
        model.addAttribute(TEMPLATE_USERS, dbServiceUser.findAll());
        model.addAttribute(TEMPLATE_ACTIONS, Routes.PAGE_ACTIONS);
        return USERS_PAGE_TEMPLATE;
    }

}
