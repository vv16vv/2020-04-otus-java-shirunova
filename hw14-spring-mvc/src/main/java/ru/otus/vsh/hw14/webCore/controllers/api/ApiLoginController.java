package ru.otus.vsh.hw14.webCore.controllers.api;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw14.webCore.controllers.dataClasses.LoginData;
import ru.otus.vsh.hw14.webCore.server.Routes;
import ru.otus.vsh.hw14.webCore.services.UserAuthService;

@RestController
public class ApiLoginController {

    private final UserAuthService userAuthService;

    public ApiLoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping(Routes.API_LOGIN)
    public RedirectView processLogin(@ModelAttribute LoginData data) {
        if (userAuthService.authenticate(data.getLogin(), data.getPassword())) {
            return new RedirectView(Routes.PAGE_ACTIONS, true);
        } else {
            return new RedirectView(Routes.PAGE_INDEX, true);
        }
    }

}
