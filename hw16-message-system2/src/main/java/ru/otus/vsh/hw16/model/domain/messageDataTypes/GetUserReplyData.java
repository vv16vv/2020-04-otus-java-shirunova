package ru.otus.vsh.hw16.model.domain.messageDataTypes;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.model.domain.Player;

@Value
public class GetUserReplyData implements MessageData {
    Player player;
}
