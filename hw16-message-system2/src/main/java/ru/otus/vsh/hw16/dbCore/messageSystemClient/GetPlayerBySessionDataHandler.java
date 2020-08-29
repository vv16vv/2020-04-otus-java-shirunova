package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.dbService.DBServiceSession;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.model.domain.Player;

import java.util.Optional;

/**
 * Исполняется клиентом [DataBaseMSClient]
 * при обработке сообщения GET_PLAYER_BY_SESSION
 */
@AllArgsConstructor
public class GetPlayerBySessionDataHandler extends SimpleReceiveRequestHandler<GetPlayerBySessionData, GetPlayerBySessionReplyData> {
    private final DBServicePlayer dbServicePlayer;
    private final DBServiceSession dbServiceSession;

    @Override
    public Optional<Message<GetPlayerBySessionReplyData>> handle(Message<GetPlayerBySessionData> msg) {
        val sessionId = msg.getBody().getSessionId();
        val session = dbServiceSession.findBySessionId(sessionId);
        Optional<Player> player = Optional.empty();
        if(session.isPresent()) {
            player = dbServicePlayer.findByLogin(session.get().getLogin());
        }
        return Optional.of(Message.buildReplyMessage(msg, new GetPlayerBySessionReplyData(sessionId, player)));
    }
}
