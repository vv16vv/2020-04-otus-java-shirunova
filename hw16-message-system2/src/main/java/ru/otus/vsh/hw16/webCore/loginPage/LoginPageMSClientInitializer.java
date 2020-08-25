package ru.otus.vsh.hw16.webCore.loginPage;

import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface LoginPageMSClientInitializer {
    MsClient loginControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);
}
