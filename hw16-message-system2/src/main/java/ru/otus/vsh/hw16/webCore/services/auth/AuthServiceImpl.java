package ru.otus.vsh.hw16.webCore.services.auth;


import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.common.CallbackReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.GetPlayerData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.GetPlayerReplyData;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class AuthServiceImpl implements AuthService {

    private final AuthServiceMSClient authServiceMSClient;

    public AuthServiceImpl(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        this.authServiceMSClient = authServiceMSClient(messageSystem, callbackRegistry);
    }

    private AuthServiceMSClient authServiceMSClient(MessageSystem messageSystem,
                                                   CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.LOGIN, new AuthDataHandler(this));
        store.addHandler(MessageType.GET_PLAYER, new CallbackReceiveRequestHandler<GetPlayerData, GetPlayerReplyData>(callbackRegistry));
        val authServiceMSClient = new AuthServiceMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(authServiceMSClient);

        return authServiceMSClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        AtomicReference<GetPlayerReplyData> answerFromDb = new AtomicReference<>(null);
        val data = new GetPlayerData(login);
        Message<GetPlayerData> message = authServiceMSClient.produceMessage(
                MsClientNames.DATA_BASE.name(),
                data, MessageType.GET_PLAYER,
                replay -> answerFromDb.set((GetPlayerReplyData) replay)
        );
        authServiceMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(answerFromDb, ref -> ref.get() != null);

        val player = answerFromDb.get().getPlayer();
        return player
                .map(value -> value.getPassword().equals(password))
                .orElse(false);
    }

}
