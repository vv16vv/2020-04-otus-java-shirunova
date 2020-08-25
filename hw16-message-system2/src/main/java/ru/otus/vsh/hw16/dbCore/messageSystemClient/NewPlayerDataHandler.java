package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.model.domain.Player;

import java.util.Optional;

/**
 * Исполняется клиентом [DataBaseMSClient]
 * при обработке сообщения NEW_PLAYER
 */
@AllArgsConstructor
public class NewPlayerDataHandler extends SimpleReceiveRequestHandler<NewPlayerData, NewPlayerReplyData> {
    private final DBServicePlayer dbServicePlayer;

    @Override
    public Optional<Message<NewPlayerReplyData>> handle(Message<NewPlayerData> msg) {
        val existPlayer = dbServicePlayer.findByLogin(msg.getBody().getLogin());
        var result = existPlayer.isEmpty();
        if (existPlayer.isEmpty()) {
            val newPlayer = Player.builder()
                    .login(msg.getBody().getLogin())
                    .name(msg.getBody().getName())
                    .password(msg.getBody().getPassword())
                    .id(0)
                    .get();
            val newId = dbServicePlayer.newObject(newPlayer);
            result = newId > 0;
        }
        return Optional.of(Message.buildReplyMessage(msg, new NewPlayerReplyData(result)));
    }
}
