package ru.otus.vsh.hw16.domain.messageSystemClient.data;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import javax.annotation.Nonnull;
import java.util.List;

@Data
@Accessors(fluent = true)
public class GameData implements MessageData {
    final GameId gameId;
    final int number;
    final List<EquationData> equations;
    final int numberOfEquations;

    int numberOfSuccess = 0;
    int index = 0;

    public GameData(GameId gameId,
                    int number,
                    @Nonnull List<EquationData> equations) {
        this.gameId = gameId;
        this.number = number;
        this.equations = equations;
        this.numberOfEquations = equations.size();
    }

    public boolean isGameOver(){
        return index == numberOfEquations;
    }
}
