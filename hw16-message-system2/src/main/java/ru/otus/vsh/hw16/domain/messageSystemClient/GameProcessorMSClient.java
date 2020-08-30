package ru.otus.vsh.hw16.domain.messageSystemClient;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class GameProcessorMSClient extends MsClientImpl implements MsClient {
    public GameProcessorMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.GAME_PROCESSOR.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
