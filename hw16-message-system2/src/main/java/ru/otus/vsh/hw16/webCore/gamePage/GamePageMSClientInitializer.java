package ru.otus.vsh.hw16.webCore.gamePage;

import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface GamePageMSClientInitializer {
    MsClient gameControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry);
}
