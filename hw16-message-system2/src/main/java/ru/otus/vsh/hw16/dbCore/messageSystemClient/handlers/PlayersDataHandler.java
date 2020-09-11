package ru.otus.vsh.hw16.dbCore.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.PlayersReplyData;
import ru.otus.vsh.hw16.messagesystem.common.EmptyMessageData;
import ru.otus.vsh.hw16.messagesystem.common.ResponseProduceRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

/**
 * Исполняется клиентом [DataBaseMSClient]
 * при обработке сообщения PLAYERS
 */
@AllArgsConstructor
public class PlayersDataHandler implements ResponseProduceRequestHandler<EmptyMessageData, PlayersReplyData> {
    private final DBServicePlayer dbServicePlayer;

    @Override
    public Optional<Message<PlayersReplyData>> handle(Message<EmptyMessageData> msg) {
        val players = dbServicePlayer.findAll();
        return Optional.of(Message.buildReplyMessage(msg, new PlayersReplyData(players)));
    }
}
