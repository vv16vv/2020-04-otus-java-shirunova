package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.dbCore.dbService.DBServiceSession;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

@Component
public class DBMSClientInitializerImpl implements DBMSClientInitializer {
    @Override
    @Bean
    public DataBaseMSClient dataBaseMSClient(MessageSystem messageSystem,
                                             CallbackRegistry callbackRegistry,
                                             DBServicePlayer dbServicePlayer,
                                             DBServiceSession dbServiceSession) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.GET_PLAYER_BY_LOGIN, new GetPlayerByLoginDataHandler(dbServicePlayer));
        store.addHandler(MessageType.GET_PLAYER_BY_SESSION, new GetPlayerBySessionDataHandler(dbServicePlayer, dbServiceSession));
        store.addHandler(MessageType.NEW_PLAYER, new NewPlayerDataHandler(dbServicePlayer));
        store.addHandler(MessageType.NEW_SESSION, new NewSessionDataHandler(dbServiceSession));
        store.addHandler(MessageType.PLAYERS, new PlayersDataHandler(dbServicePlayer));
        val databaseMsClient = new DataBaseMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(databaseMsClient);

        return databaseMsClient;
    }
}
