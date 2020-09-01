package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class NewGameData implements MessageData {
    String gameId;
    int number = 0;
}
