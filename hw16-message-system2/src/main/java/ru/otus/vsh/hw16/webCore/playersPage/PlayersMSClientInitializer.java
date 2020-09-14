package ru.otus.vsh.hw16.webCore.playersPage;

import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface PlayersMSClientInitializer {
    MsClient playersControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);
}
