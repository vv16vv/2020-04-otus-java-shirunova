package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw14.webCore.server.Routes;

@Controller
public class PageActionsController {
    private static final String TEMPLATE_NEW_USER = "newUser";
    private static final String TEMPLATE_ALL_USERS = "allUsers";
    private static final String ACTIONS_PAGE_TEMPLATE = "actions.html";

    public PageActionsController() {
    }

    @GetMapping(Routes.PAGE_ACTIONS)
    public String getActionsPage(Model model) {
        model.addAttribute(TEMPLATE_NEW_USER, Routes.PAGE_NEW_USER);
        model.addAttribute(TEMPLATE_ALL_USERS, Routes.PAGE_USERS);
        return ACTIONS_PAGE_TEMPLATE;
    }

}
