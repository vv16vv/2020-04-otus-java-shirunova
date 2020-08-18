package ru.otus.vsh.hw16.model.domain.messageDataTypes;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class EquationResultData implements MessageData {
    int result;
    String equationId;
    String sessionId;
}
