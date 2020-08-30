package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.vsh.hw16.domain.model.Operation;

@Data
@RequiredArgsConstructor
public class EquationMessage {
    final int number1;
    final int number2;
    final Operation operation;
    int result = 0;
    boolean isCorrect = false;
}
