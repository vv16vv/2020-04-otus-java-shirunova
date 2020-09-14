package ru.otus.vsh.hw16.dbCore.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.dbService.DBServiceSession;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionReplyData;
import ru.otus.vsh.hw16.domain.model.Player;
import ru.otus.vsh.hw16.messagesystem.common.ResponseProduceRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

/**
 * Исполняется клиентом [DataBaseMSClient]
 * при обработке сообщения GET_PLAYER_BY_SESSION
 */
@AllArgsConstructor
public class GetPlayerBySessionDataHandler implements ResponseProduceRequestHandler<GetPlayerBySessionData, GetPlayerBySessionReplyData> {
    private final DBServicePlayer dbServicePlayer;
    private final DBServiceSession dbServiceSession;

    @Override
    public Optional<Message<GetPlayerBySessionReplyData>> handle(Message<GetPlayerBySessionData> msg) {
        val sessionId = msg.getBody().getSessionId();
        val session = dbServiceSession.findBySessionId(sessionId);
        Optional<Player> player = Optional.empty();
        if (session.isPresent()) {
            player = dbServicePlayer.findByLogin(session.get().getLogin());
        }
        return Optional.of(Message.buildReplyMessage(msg, new GetPlayerBySessionReplyData(sessionId, player)));
    }
}
