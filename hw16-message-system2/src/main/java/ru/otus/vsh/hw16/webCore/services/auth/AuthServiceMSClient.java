package ru.otus.vsh.hw16.webCore.services.auth;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class AuthServiceMSClient extends MsClientImpl {
    public AuthServiceMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.AUTH_SERVICE.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
