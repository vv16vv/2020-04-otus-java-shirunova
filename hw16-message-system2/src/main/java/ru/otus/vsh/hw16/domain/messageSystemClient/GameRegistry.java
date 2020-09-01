package ru.otus.vsh.hw16.domain.messageSystemClient;

import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameId;

public interface GameRegistry {
    void put(GameId gameId, GameData data);

    GameData get(GameId gameId);

    void remove(GameId gameId);
}
