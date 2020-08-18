package ru.otus.vsh.hw16.model.domain.messageDataTypes;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class EndOfGameData implements MessageData {
    int numberOfEquations;
    int numberOfSuccess;
    String sessionId;
}
