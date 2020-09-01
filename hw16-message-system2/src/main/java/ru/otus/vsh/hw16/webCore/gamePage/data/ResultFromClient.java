package ru.otus.vsh.hw16.webCore.gamePage.data;

import lombok.Value;

@Value
public class ResultFromClient {
    String gameId;
    int eqIndex;
    int answer;
}
