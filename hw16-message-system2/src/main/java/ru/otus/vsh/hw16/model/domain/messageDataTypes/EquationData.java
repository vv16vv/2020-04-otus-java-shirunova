package ru.otus.vsh.hw16.model.domain.messageDataTypes;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.model.domain.Operation;

@Value
public class EquationData implements MessageData {
    int number1;
    int number2;
    Operation operation;
    String equationId;
    String sessionId;
}
