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
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionReplyData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.NewGameData;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.webCore.gamePage.data.CorrectToClient;
import ru.otus.vsh.hw16.webCore.gamePage.data.EquationToClient;
import ru.otus.vsh.hw16.webCore.gamePage.data.ResultFromClient;
import ru.otus.vsh.hw16.webCore.gamePage.data.ResultToClient;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
@Slf4j
public class GamePageController {

    private static final String TEMPLATE_PLAYER_NAME = "playerName";
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

    @MessageMapping(Routes.API_GAME_START + ".{sessionId}")
    public void startGame(@PathVariable String sessionId, NewGameData newGameData) {
        AtomicReference<GameData> answer = new AtomicReference<>(null);
        val message = gameControllerMSClient.produceMessage(
                MsClientNames.GAME_KEEPER.name(),
                newGameData, MessageType.NEW_GAME,
                replay -> answer.set((GameData) replay)
        );
        gameControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(
                answer,
                ref -> ref.get() != null);

        sendGameDataToClient(answer.get());
    }

    @MessageMapping(Routes.API_ANSWER + ".{gameId}")
    public void processEquationResult(@DestinationVariable String gameId, ResultFromClient resultFromClient) {
        log.info("got resultFromClient:{}, gameId:{}", resultFromClient, gameId);
        AtomicReference<GameData> answer = new AtomicReference<>(null);
        val message = gameControllerMSClient.produceMessage(
                MsClientNames.GAME_KEEPER.name(),
                resultFromClient, MessageType.NEXT_TASK,
                replay -> answer.set((GameData) replay)
        );
        gameControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(
                answer,
                ref -> ref.get() != null);

        sendGameDataToClient(answer.get());
    }


    private void sendGameDataToClient(@Nonnull GameData data) {
        if (data.index() > 0) {
            template.convertAndSend(Routes.API_TOPIC_CORRECT + "." + data.gameId().getId(), new CorrectToClient(
                    data.gameId().getId(),
                    data.index() - 1,
                    data.equations().get(data.index() - 1).isCorrect()
            ));
        }
        if (!data.isGameOver()) {
            template.convertAndSend(Routes.API_TOPIC_EQUATION + "." + data.gameId().getId(), new EquationToClient(
                    data.gameId().getId(),
                    data.index(),
                    data.equations().get(data.index()).equation().toString()
            ));
        }
        template.convertAndSend(Routes.API_TOPIC_RESULT + "." + data.gameId().getId(), new ResultToClient(
                data.gameId().getId(),
                data.numberOfSuccess(),
                data.numberOfEquations(),
                data.index()
        ));
    }
}
