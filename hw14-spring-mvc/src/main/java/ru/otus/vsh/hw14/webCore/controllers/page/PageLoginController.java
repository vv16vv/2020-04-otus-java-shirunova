package ru.otus.vsh.hw14.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw14.webCore.controllers.dataClasses.LoginData;
import ru.otus.vsh.hw14.webCore.server.Routes;
import ru.otus.vsh.hw14.webCore.services.UserAuthService;

@Controller
public class PageLoginController {
    private static final String TEMPLATE_LOGIN_FORM = "login";
    private static final String TEMPLATE_LOGIN_DATA = "data";
    private static final String INDEX_PAGE_TEMPLATE = "index.html";
    private final UserAuthService userAuthService;

    public PageLoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping(Routes.PAGE_INDEX)
    public String getLoginPage(Model model) {
        model.addAttribute(TEMPLATE_LOGIN_FORM, Routes.PAGE_INDEX);
        model.addAttribute(TEMPLATE_LOGIN_DATA, new LoginData());
        return INDEX_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.PAGE_INDEX)
    public RedirectView processLogin(@ModelAttribute LoginData data) {
        if (userAuthService.authenticate(data.getLogin(), data.getPassword())) {
            return new RedirectView(Routes.PAGE_ACTIONS, true);
        } else {
            return new RedirectView(Routes.PAGE_INDEX, true);
        }
    }

}
