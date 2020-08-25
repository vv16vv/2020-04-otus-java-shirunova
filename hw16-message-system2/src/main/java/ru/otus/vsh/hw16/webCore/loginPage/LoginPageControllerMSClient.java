package ru.otus.vsh.hw16.webCore.loginPage;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class LoginPageControllerMSClient extends MsClientImpl implements MsClient {
    public LoginPageControllerMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.LOGIN_CONTROLLER.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
