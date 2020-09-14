package ru.otus.vsh.hw16.webCore.gamePage.data;

import lombok.Value;

@Value
public class CorrectToClient {
    String gameId;
    int eqIndex;
    boolean isCorrect;
}
