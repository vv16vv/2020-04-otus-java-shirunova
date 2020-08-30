package ru.otus.vsh.hw16.dbCore.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerByLoginData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerByLoginReplyData;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

/**
 * Исполняется клиентом [DataBaseMSClient]
 * при обработке сообщения GET_PLAYER_BY_LOGIN
 */
@AllArgsConstructor
public class GetPlayerByLoginDataHandler extends SimpleReceiveRequestHandler<GetPlayerByLoginData, GetPlayerByLoginReplyData> {
    private final DBServicePlayer dbServicePlayer;

    @Override
    public Optional<Message<GetPlayerByLoginReplyData>> handle(Message<GetPlayerByLoginData> msg) {
        val login = msg.getBody().getLogin();
        val player = dbServicePlayer.findByLogin(login);
        return Optional.of(Message.buildReplyMessage(msg, new GetPlayerByLoginReplyData(player)));
    }
}
