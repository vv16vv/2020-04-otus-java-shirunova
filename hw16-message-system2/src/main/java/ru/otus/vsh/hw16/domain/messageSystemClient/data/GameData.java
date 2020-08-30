package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import java.util.List;

@Value
public class GameData implements MessageData {
    String sessionId;
    String gameId;
    int number;
    List<EquationData> equations;
}
