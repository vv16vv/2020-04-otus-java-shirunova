package ru.otus.vsh.hw16.domain.messageSystemClient;

import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface GameKeeperMSClientInitializer {
    MsClient gameKeeperMSClient(MessageSystem messageSystem,
                                CallbackRegistry callbackRegistry,
                                GameRegistry gameRegistry);
}
