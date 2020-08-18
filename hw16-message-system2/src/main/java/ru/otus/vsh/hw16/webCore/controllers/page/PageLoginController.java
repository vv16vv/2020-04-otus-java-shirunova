package ru.otus.vsh.hw16.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw16.webCore.controllers.dataClasses.LoginData;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.UserAuthService;

@Controller
public class PageLoginController {
    private static final String TEMPLATE_LOGIN_FORM = "login";
    private static final String TEMPLATE_LOGIN_DATA = "data";
    private static final String INDEX_PAGE_TEMPLATE = "index.html";
    private final UserAuthService userAuthService;

    public PageLoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping(Routes.ROOT)
    public String getLoginPage(Model model) {
        model.addAttribute(TEMPLATE_LOGIN_FORM, Routes.ROOT);
        model.addAttribute(TEMPLATE_LOGIN_DATA, new LoginData());
        return INDEX_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.ROOT)
    public RedirectView processLogin(@ModelAttribute LoginData data) {
        if (userAuthService.authenticate(data.getLogin(), data.getPassword())) {
            return new RedirectView(Routes.GAME, true);
        } else {
            return new RedirectView(Routes.ROOT, true);
        }
    }

}
