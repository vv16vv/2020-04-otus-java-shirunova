package ru.otus.vsh.hw16.webCore.msClients;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.webCore.server.Routes;

@Controller
public class PagePlayersController {

    private static final String TEMPLATE_PLAYERS = "players";
    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final DBServicePlayer dbServicePlayer;

    public PagePlayersController(DBServicePlayer dbServicePlayer) {
        this.dbServicePlayer = dbServicePlayer;
    }

    @GetMapping(Routes.PLAYERS)
    public String getUsersPage(Model model) {
        model.addAttribute(TEMPLATE_PLAYERS, dbServicePlayer.findAll());
        return USERS_PAGE_TEMPLATE;
    }

}
