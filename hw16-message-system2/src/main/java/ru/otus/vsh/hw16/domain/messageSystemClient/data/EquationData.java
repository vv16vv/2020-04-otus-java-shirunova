package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.domain.model.Operation;

@Value
public class EquationData implements MessageData {
    int number1;
    int number2;
    Operation operation;
    String equationId;
    String sessionId;
}
