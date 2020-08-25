package ru.otus.vsh.hw16.webCore.services;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistryImpl;
import ru.otus.vsh.hw16.model.GameProcessorMSClient;
import ru.otus.vsh.hw16.webCore.msClients.GameControllerMSClient;

@Component
public class MsClientsInitializerImpl implements MsClientsInitializer {
    @Override
    @Bean
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
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
    // not ready
    public GameControllerMSClient gameControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        val gameControllerMSClient = new GameControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(gameControllerMSClient);

        return gameControllerMSClient;
    }
}
