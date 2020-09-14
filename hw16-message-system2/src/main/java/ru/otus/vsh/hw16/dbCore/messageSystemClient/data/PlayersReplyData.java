package ru.otus.vsh.hw16.dbCore.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.domain.model.Player;

import java.util.List;

@Value
public class PlayersReplyData implements MessageData {
    List<Player> players;
}
