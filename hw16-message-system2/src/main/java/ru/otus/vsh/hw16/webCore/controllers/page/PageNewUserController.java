package ru.otus.vsh.hw16.webCore.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.model.domain.Player;
import ru.otus.vsh.hw16.webCore.controllers.dataClasses.UserData;
import ru.otus.vsh.hw16.webCore.server.Routes;

@Controller
public class PageNewUserController {
    private static final String TEMPLATE_ROUTE = "route";
    private static final String TEMPLATE_PLAYER = "player";
    private static final String PLAYER_PAGE_TEMPLATE = "new-player.html";
    private final DBServicePlayer dbServicePlayer;

    public PageNewUserController(DBServicePlayer dbServicePlayer) {
        this.dbServicePlayer = dbServicePlayer;
    }

    @GetMapping(Routes.NEW_PLAYER)
    public String getNewUserPage(Model model) {
        model.addAttribute(TEMPLATE_ROUTE, Routes.NEW_PLAYER);

        model.addAttribute(TEMPLATE_PLAYER, new UserData());
        return PLAYER_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.NEW_PLAYER)
    public RedirectView addUser(@ModelAttribute UserData userData) {
        var player = Player.builder()
                .login(userData.getLogin())
                .name(userData.getName())
                .password(userData.getPassword())
                .get();

        dbServicePlayer.saveObject(player);
        return new RedirectView(Routes.PLAYERS, true);
    }

}
