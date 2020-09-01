package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import java.util.List;

@Value
public class GameReplayData implements MessageData {
    String sessionId;
    String gameId;
    int number;
    List<EquationData> equations;
    int numberOfSuccess = 0;
    int numberOfEquations;
    int index = 0;

    public GameReplayData(String sessionId,
                          String gameId,
                          int number,
                          List<EquationData> equations) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.number = number;
        this.equations = equations;
        this.numberOfEquations = equations.size();
    }
}
