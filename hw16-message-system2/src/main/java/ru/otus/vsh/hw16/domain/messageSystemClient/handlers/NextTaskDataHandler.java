package ru.otus.vsh.hw16.domain.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.domain.messageSystemClient.GameRegistry;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameId;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.webCore.gamePage.data.ResultFromClient;

import java.util.Optional;

/**
 * Исполняется клиентом [GameKeeperMSClient]
 * при обработке сообщения NEXT_TASK
 */
@AllArgsConstructor
public class NextTaskDataHandler extends SimpleReceiveRequestHandler<ResultFromClient, GameData> {
    private final GameRegistry gameRegistry;

    @Override
    public Optional<Message<GameData>> handle(Message<ResultFromClient> msg) {
        val resultFromClient = msg.getBody();
        val data = gameRegistry.get(new GameId(resultFromClient.getGameId()));
        val isCorrect = data
                .equations()
                .get(resultFromClient.getEqIndex())
                .actualResult(resultFromClient.getAnswer())
                .isCorrect();
        if (isCorrect) {
            data.numberOfSuccess(data.numberOfSuccess() + 1);
        }
        data.index(resultFromClient.getEqIndex());

        if (data.isGameOver()) {
            gameRegistry.remove(data.gameId());
        } else {
            gameRegistry.put(data.gameId(), data);
        }

        return Optional.of(Message.buildReplyMessage(msg, data));
    }
}
