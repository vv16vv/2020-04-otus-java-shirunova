package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw14.webCore.controllers.dataClasses.LoginData;
import ru.otus.vsh.hw14.webCore.server.Routes;

@Controller
public class PageLoginController {
    private static final String TEMPLATE_LOGIN_FORM = "login";
    private static final String TEMPLATE_LOGIN_DATA = "data";
    private static final String INDEX_PAGE_TEMPLATE = "index.html";

    public PageLoginController() {
    }

    @GetMapping(Routes.PAGE_INDEX)
    public String getLoginPage(Model model) {
        model.addAttribute(TEMPLATE_LOGIN_FORM, Routes.API_LOGIN);
        model.addAttribute(TEMPLATE_LOGIN_DATA, new LoginData());
        return INDEX_PAGE_TEMPLATE;
    }

}
