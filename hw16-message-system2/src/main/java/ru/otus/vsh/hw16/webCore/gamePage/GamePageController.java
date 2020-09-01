package ru.otus.vsh.hw16.webCore.gamePage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionReplyData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameReplayData;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.webCore.gamePage.data.EquationToClient;
import ru.otus.vsh.hw16.webCore.gamePage.data.ResultFromClient;
import ru.otus.vsh.hw16.webCore.gamePage.data.ResultToClient;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
@Slf4j
public class GamePageController {

    private static final String TEMPLATE_PLAYER_NAME = "player-name";
    private static final String TEMPLATE_SESSION_ID = "sessionId";
    private static final String GAME_PAGE_TEMPLATE = "game.html";

    private final SimpMessagingTemplate template;
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
    public void startGame(@PathVariable String sessionId, @ModelAttribute GameData gameData) {
        AtomicReference<GameReplayData> answer = new AtomicReference<>(null);
        val message = gameControllerMSClient.produceMessage(
                MsClientNames.EQUATION_PREPARER.name(),
                gameData, MessageType.NEW_GAME,
                replay -> answer.set((GameReplayData) replay)
        );
        gameControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(
                answer,
                ref -> ref.get() != null);

        val data = answer.get();
        template.convertAndSend(Routes.API_TOPIC_EQUATION, new EquationToClient(
                gameData.getGameId(),
                0,
                data.getEquations().get(0).getEquation().toString()
        ));
        template.convertAndSend(Routes.API_TOPIC_RESULT, new ResultToClient(
                gameData.getGameId(),
                data.getNumberOfSuccess(),
                data.getNumberOfEquations(),
                0
        ));
    }

    @MessageMapping(Routes.API_TOPIC_ANSWER + ".{gameId}")
    public void processEquationResult(@DestinationVariable String gameId, ResultFromClient resultFromClient) {
        log.info("got resultFromClient:{}, gameId:{}", resultFromClient, gameId);
        // go to DB to get the object with all equations
        val data = answer.get();
        template.convertAndSend(Routes.API_TOPIC_EQUATION, new EquationToClient(
                gameData.getGameId(),
                0,
                data.getEquations().get(0).getEquation().toString()
        ));
        template.convertAndSend(Routes.API_TOPIC_RESULT, new ResultToClient(
                gameData.getGameId(),
                data.getNumberOfSuccess(),
                data.getNumberOfEquations(),
                0
        ));
    }

}
