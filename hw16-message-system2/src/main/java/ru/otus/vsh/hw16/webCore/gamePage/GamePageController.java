package ru.otus.vsh.hw16.webCore.gamePage;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionReplyData;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.NewGameData;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
public class GamePageController {

    private static final String TEMPLATE_PLAYER_NAME = "player-name";
    private static final String TEMPLATE_SESSION_ID = "sessionId";
    private static final String GAME_PAGE_TEMPLATE = "game.html";

    private final GameControllerMSClient gameControllerMSClient;

    @GetMapping(Routes.GAME + "/{sessionId}")
    public String getGamePage(Model model, @PathVariable String sessionId) {
        AtomicReference<GetPlayerBySessionReplyData> answer = new AtomicReference<>(null);
        val message = gameControllerMSClient.produceMessage(
                MsClientNames.DATA_BASE.name(),
                new GetPlayerBySessionData(sessionId), MessageType.GET_PLAYER_BY_SESSION,
                replay -> answer.set((GetPlayerBySessionReplyData) replay)
        );
        gameControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(answer,
                ref -> ref.get() != null);

        val player = answer.get().getPlayer().orElseThrow();

        model.addAttribute(TEMPLATE_PLAYER_NAME, player.getName());
        model.addAttribute(TEMPLATE_SESSION_ID, sessionId);
        return GAME_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.GAME + "/{sessionId}")
    public void startGame(@PathVariable String sessionId, @ModelAttribute NewGameData newGameData) {
        // Послать мессадж GameProcessor, чтобы тот сгенерировал примеры для новой игры и сохранил в базу
        AtomicReference<GetPlayerBySessionReplyData> answer = new AtomicReference<>(null);
        val message = gameControllerMSClient.produceMessage(
                MsClientNames.GAME_PROCESSOR.name(),
                new GetPlayerBySessionData(sessionId), MessageType.GET_PLAYER_BY_SESSION,
                replay -> answer.set((GetPlayerBySessionReplyData) replay)
        );
        gameControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(
                answer,
                ref -> ref.get() != null);

        // Послать на end point equation первый пример
    }
}
