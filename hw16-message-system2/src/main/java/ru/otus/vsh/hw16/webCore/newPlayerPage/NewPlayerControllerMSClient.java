package ru.otus.vsh.hw16.webCore.newPlayerPage;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class NewPlayerControllerMSClient extends MsClientImpl implements MsClient {
    public NewPlayerControllerMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.NEW_PLAYER_CONTROLLER.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
