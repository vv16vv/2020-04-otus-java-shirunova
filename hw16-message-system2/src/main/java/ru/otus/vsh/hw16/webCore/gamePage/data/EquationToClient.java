package ru.otus.vsh.hw16.webCore.gamePage.data;

import lombok.Value;

@Value
public class EquationToClient {
    String gameId;
    int eqIndex;
    String eqText;
}
