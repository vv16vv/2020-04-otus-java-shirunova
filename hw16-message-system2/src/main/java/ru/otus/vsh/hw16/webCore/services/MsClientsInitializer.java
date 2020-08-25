package ru.otus.vsh.hw16.webCore.services;

import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface MsClientsInitializer {
    CallbackRegistry callbackRegistry();

    MsClient gameProcessorMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);

    MsClient loginControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);

    MsClient newPlayerControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);

    MsClient gameControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);
}
