package ru.otus.vsh.hw16.webCore.gamePage.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class ResultFromClient implements MessageData {
    String gameId;
    int eqIndex;
    int answer;
}
