package ru.otus.vsh.hw16.domain.messageSystemClient;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.domain.messageSystemClient.handlers.NewGameDataHandler;
import ru.otus.vsh.hw16.domain.messageSystemClient.handlers.NextTaskDataHandler;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

@Component
public class GameKeeperMSClientInitializerImpl implements GameKeeperMSClientInitializer {
    @Override
    @Bean
    public GameKeeperMSClient gameKeeperMSClient(MessageSystem messageSystem,
                                                 CallbackRegistry callbackRegistry,
                                                 GameRegistry gameRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.NEW_GAME, new NewGameDataHandler(gameRegistry));
        store.addHandler(MessageType.NEXT_TASK, new NextTaskDataHandler(gameRegistry));
        val gameKeeperMSClient = new GameKeeperMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(gameKeeperMSClient);

        return gameKeeperMSClient;
    }
}
