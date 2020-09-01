package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Data;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Data
public class NewGameData implements MessageData {
    String gameId;
    int number = 0;
}
