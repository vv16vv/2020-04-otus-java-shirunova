package ru.otus.vsh.hw16.webCore.gamePage.data;

import lombok.Value;

@Value
public class ResultToClient {
    String gameId;
    int numberOfSuccess;
    int numberOfAll;
    int eqIndex;
}
