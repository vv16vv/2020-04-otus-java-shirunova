package ru.otus.vsh.hw16.webCore.gamePage;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.GetPlayerBySessionData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.GetPlayerBySessionReplyData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.PlayersReplyData;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.common.EmptyMessageData;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.model.domain.messageDataTypes.GameData;
import ru.otus.vsh.hw16.webCore.playersPage.PlayersControllerMSClient;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
public class GamePageController {

    private static final String TEMPLATE_GAME = "game";
    private static final String TEMPLATE_START = "startGame";
    private static final String TEMPLATE_CONT = "contGame";
    private static final String GAME_PAGE_TEMPLATE = "game.html";

    private final GameControllerMSClient gameControllerMSClient;

    @GetMapping(Routes.GAME + "/{sessionId}")
    public String getGamePage(Model model, @PathVariable String sessionId) {
        // get player by sessionId
        AtomicReference<GetPlayerBySessionReplyData> answer = new AtomicReference<>(null);
        val message = gameControllerMSClient.produceMessage(
                MsClientNames.DATA_BASE.name(),
                new GetPlayerBySessionData(sessionId), MessageType.GET_PLAYER_BY_SESSION,
                replay -> answer.set((GetPlayerBySessionReplyData) replay)
        );
        gameControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(answer,
                ref -> ref.get() != null);

        //
        val game = new GameData(answer.get().getPlayer().get());

        model.addAttribute(TEMPLATE_GAME, game);
        model.addAttribute(TEMPLATE_START, Routes.API_START);
        model.addAttribute(TEMPLATE_CONT, Routes.API_CONT);
        return GAME_PAGE_TEMPLATE;
    }

}
