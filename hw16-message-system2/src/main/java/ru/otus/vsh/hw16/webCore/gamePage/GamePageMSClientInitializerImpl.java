package ru.otus.vsh.hw16.webCore.gamePage;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerBySessionReplyData;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.common.CallbackReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

@Component
public class GamePageMSClientInitializerImpl implements GamePageMSClientInitializer {
    @Override
    @Bean
    public GameControllerMSClient gameControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.GET_PLAYER_BY_SESSION, new CallbackReceiveRequestHandler<GetPlayerBySessionData, GetPlayerBySessionReplyData>(callbackRegistry));
        val gameControllerMSClient = new GameControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(gameControllerMSClient);

        return gameControllerMSClient;
    }
}
