package ru.otus.vsh.hw16.domain.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameReplayData;
import ru.otus.vsh.hw16.domain.util.EquationPreparer;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

/**
 * Исполняется клиентом [EquationPreparerMSClient]
 * при обработке сообщения NEW_GAME
 */
@AllArgsConstructor
public class EquationPreparerDataHandler extends SimpleReceiveRequestHandler<GameData, GameReplayData> {

    @Override
    public Optional<Message<GameReplayData>> handle(Message<GameData> msg) {
        val initialGameData = msg.getBody();
        val data = new GameReplayData(
                initialGameData.getSessionId(),
                initialGameData.getGameId(),
                initialGameData.getNumber(),
                EquationPreparer.get(initialGameData.getNumber())
        );
        return Optional.of(Message.buildReplyMessage(msg, data));
    }
}
