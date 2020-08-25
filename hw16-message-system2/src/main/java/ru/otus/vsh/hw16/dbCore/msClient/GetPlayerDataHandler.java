package ru.otus.vsh.hw16.dbCore.msClient;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.model.domain.messageDataTypes.GetPlayerData;
import ru.otus.vsh.hw16.model.domain.messageDataTypes.GetPlayerReplyData;

import java.util.Optional;

/**
 * Исполняется клиентом [DataBaseMSClient]
 * при обработке сообщения GET_PLAYER
 */
@AllArgsConstructor
public class GetPlayerDataHandler extends SimpleReceiveRequestHandler<GetPlayerData, GetPlayerReplyData> {
    private final DBServicePlayer dbServicePlayer;

    @Override
    public Optional<Message<GetPlayerReplyData>> handle(Message<GetPlayerData> msg) {
        val login = msg.getBody().getLogin();
        val player = dbServicePlayer.findByLogin(login);
        if(player.isPresent()){
            return Optional.of(Message.buildReplyMessage(msg, new GetPlayerReplyData(player)));
        }
        else return Optional.empty();
    }
}
