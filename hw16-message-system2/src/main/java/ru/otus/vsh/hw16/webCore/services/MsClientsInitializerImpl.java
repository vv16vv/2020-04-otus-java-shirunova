package ru.otus.vsh.hw16.webCore.services;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.msClient.DataBaseMSClient;
import ru.otus.vsh.hw16.dbCore.msClient.GetPlayerDataHandler;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistryImpl;
import ru.otus.vsh.hw16.messagesystem.common.CallbackReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.model.GameProcessorMSClient;
import ru.otus.vsh.hw16.webCore.msClients.GameControllerMSClient;
import ru.otus.vsh.hw16.webCore.msClients.LoginControllerMSClient;
import ru.otus.vsh.hw16.webCore.msClients.NewPlayerControllerMSClient;
import ru.otus.vsh.hw16.webCore.services.auth.AuthData;
import ru.otus.vsh.hw16.webCore.services.auth.AuthReplyData;

@Component
public class MsClientsInitializerImpl implements MsClientsInitializer {
    @Override
    @Bean
    public CallbackRegistry callbackRegistry(){
        return new CallbackRegistryImpl();
    }

    @Override
    @Bean
    public DataBaseMSClient dataBaseMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry, DBServicePlayer dbServicePlayer) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.GET_PLAYER, new GetPlayerDataHandler(dbServicePlayer));
        val databaseMsClient = new DataBaseMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(databaseMsClient);

        return databaseMsClient;
    }

    @Override
    @Bean
    // not ready
    public GameProcessorMSClient gameProcessorMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        val gameProcessorMSClient = new GameProcessorMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(gameProcessorMSClient);

        return gameProcessorMSClient;
    }

    @Override
    @Bean
    public LoginControllerMSClient loginControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.LOGIN, new CallbackReceiveRequestHandler<AuthData, AuthReplyData>(callbackRegistry));
        val loginControllerMSClient = new LoginControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(loginControllerMSClient);

        return loginControllerMSClient;
    }

    @Override
    @Bean
    // not ready
    public NewPlayerControllerMSClient newPlayerControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        val newPlayerControllerMSClient = new NewPlayerControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(newPlayerControllerMSClient);

        return newPlayerControllerMSClient;
    }

    @Override
    @Bean
    // not ready
    public GameControllerMSClient gameControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        val gameControllerMSClient = new GameControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(gameControllerMSClient);

        return gameControllerMSClient;
    }
}
