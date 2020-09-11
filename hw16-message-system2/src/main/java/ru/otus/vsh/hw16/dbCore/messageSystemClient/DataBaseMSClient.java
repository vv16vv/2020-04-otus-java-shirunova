package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class DataBaseMSClient extends MsClientImpl {
    public DataBaseMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.DATA_BASE.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
