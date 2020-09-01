package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.domain.model.Equation;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class EquationData implements MessageData {
    Equation equation;
    int actualResult = 0;
}
