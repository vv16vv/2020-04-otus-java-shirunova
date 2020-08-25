package ru.otus.vsh.hw16.model.domain.messageDataTypes;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.model.domain.Player;

import java.util.Optional;

@Value
public class GetPlayerReplyData implements MessageData {
    Optional<Player> player;
}
