package ru.otus.vsh.hw16.webCore.newPlayerPage;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.NewPlayerData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.NewPlayerReplyData;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.common.CallbackReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

@Component
public class NewPlayerMSClientInitializerImpl implements NewPlayerMSClientInitializer {
    @Override
    @Bean
    public NewPlayerControllerMSClient newPlayerControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.NEW_PLAYER, new CallbackReceiveRequestHandler<NewPlayerData, NewPlayerReplyData>(callbackRegistry));
        val newPlayerControllerMSClient = new NewPlayerControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(newPlayerControllerMSClient);

        return newPlayerControllerMSClient;
    }
}
