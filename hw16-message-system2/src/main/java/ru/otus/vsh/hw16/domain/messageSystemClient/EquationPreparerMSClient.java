package ru.otus.vsh.hw16.domain.messageSystemClient;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class EquationPreparerMSClient extends MsClientImpl implements MsClient {
    public EquationPreparerMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.EQUATION_PREPARER.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
