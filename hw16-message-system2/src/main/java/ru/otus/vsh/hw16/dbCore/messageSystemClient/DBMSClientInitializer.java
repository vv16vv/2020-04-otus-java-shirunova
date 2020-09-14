package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.dbService.DBServiceSession;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.MsClient;

public interface DBMSClientInitializer {
    MsClient dataBaseMSClient(MessageSystem messageSystem,
                              CallbackRegistry callbackRegistry,
                              DBServicePlayer dbServicePlayer,
                              DBServiceSession dbServiceSession);
}
