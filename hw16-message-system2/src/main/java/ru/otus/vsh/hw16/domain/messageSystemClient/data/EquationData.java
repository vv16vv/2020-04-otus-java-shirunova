package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.otus.vsh.hw16.domain.model.Equation;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Data
@Accessors(fluent = true)
public class EquationData implements MessageData {
    final Equation equation;
    int actualResult = 0;

    public boolean isCorrect() {
        return actualResult == equation.getResult();
    }

}
