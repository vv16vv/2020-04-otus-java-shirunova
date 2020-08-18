package ru.otus.vsh.hw16.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw16.webCore.server.Routes;

@Controller
public class PageActionsController {
    private static final String TEMPLATE_NEW_USER = "newUser";
    private static final String TEMPLATE_ALL_USERS = "allUsers";
    private static final String ACTIONS_PAGE_TEMPLATE = "actions.html";

    public PageActionsController() {
    }

    @GetMapping(Routes.GAME)
    public String getActionsPage(Model model) {
        model.addAttribute(TEMPLATE_NEW_USER, Routes.NEW_PLAYER);
        model.addAttribute(TEMPLATE_ALL_USERS, Routes.PLAYERS);
        return ACTIONS_PAGE_TEMPLATE;
    }

}
