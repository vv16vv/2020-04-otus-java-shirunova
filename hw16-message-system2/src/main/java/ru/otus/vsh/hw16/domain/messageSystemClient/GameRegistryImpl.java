package ru.otus.vsh.hw16.domain.messageSystemClient;

import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameData;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.GameId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameRegistryImpl implements GameRegistry {

    private final Map<GameId, GameData> gameRegistry = new ConcurrentHashMap<>();

    @Override
    public void put(GameId gameId, GameData data) {
        if (gameRegistry.containsKey(gameId)) {
            gameRegistry.replace(gameId, data);
        } else {
            gameRegistry.put(gameId, data);
        }
    }

    @Override
    public GameData get(GameId gameId) {
        return gameRegistry.get(gameId);
    }

    @Override
    public void remove(GameId gameId) {
        gameRegistry.remove(gameId);
    }

}
