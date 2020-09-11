package ru.otus.vsh.hw16.domain.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.domain.messageSystemClient.GameRegistry;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameId;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.NewGameData;
import ru.otus.vsh.hw16.domain.util.EquationPreparer;
import ru.otus.vsh.hw16.messagesystem.common.ResponseProduceRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

/**
 * Исполняется клиентом [GameKeeperMSClient]
 * при обработке сообщения NEW_GAME
 */
@AllArgsConstructor
public class NewGameDataHandler implements ResponseProduceRequestHandler<NewGameData, GameData> {
    private final GameRegistry gameRegistry;

    @Override
    public Optional<Message<GameData>> handle(Message<NewGameData> msg) {
        val initialGameData = msg.getBody();
        val data = new GameData(
                new GameId(initialGameData.getGameId()),
                initialGameData.getNumber(),
                EquationPreparer.get(initialGameData.getNumber())
        );
        gameRegistry.put(data.gameId(), data);
        return Optional.of(Message.buildReplyMessage(msg, data));
    }
}
