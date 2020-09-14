package ru.otus.vsh.hw16.webCore.playersPage;

import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClientImpl;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

public class PlayersControllerMSClient extends MsClientImpl {
    public PlayersControllerMSClient(MessageSystem messageSystem, HandlersStore handlersStore, CallbackRegistry callbackRegistry) {
        super(MsClientNames.PLAYERS_CONTROLLER.name(), messageSystem, handlersStore, callbackRegistry);
    }
}
