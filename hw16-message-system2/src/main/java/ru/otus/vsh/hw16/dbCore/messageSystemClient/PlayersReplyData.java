package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.model.domain.Player;

import java.util.List;

@Value
public class PlayersReplyData implements MessageData {
    List<Player> players;
}
