package ru.otus.vsh.hw16.webCore.newPlayerPage;

import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface NewPlayerMSClientInitializer {
    MsClient newPlayerControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);
}
