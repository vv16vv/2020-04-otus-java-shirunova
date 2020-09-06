package ru.otus.vsh.hw16.webCore.services.auth;


import lombok.val;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerByLoginData;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.GetPlayerByLoginReplyData;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.common.CallbackReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import java.util.concurrent.CountDownLatch;
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
        store.addHandler(MessageType.GET_PLAYER_BY_LOGIN, new CallbackReceiveRequestHandler<GetPlayerByLoginData, GetPlayerByLoginReplyData>(callbackRegistry));
        val authServiceMSClient = new AuthServiceMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(authServiceMSClient);

        return authServiceMSClient;
    }

    @Override
    public boolean authenticate(String login, String password) {
        val latch = new CountDownLatch(1);
        val isAuthenticated = new AtomicReference<>(false);
        Message<GetPlayerByLoginData> message = authServiceMSClient.produceMessage(
                MsClientNames.DATA_BASE.name(),
                new GetPlayerByLoginData(login),
                MessageType.GET_PLAYER_BY_LOGIN,
                replay -> {
                    isAuthenticated.set(((GetPlayerByLoginReplyData) replay)
                            .getPlayer()
                            .map(value -> value.getPassword().equals(password))
                            .orElse(false));
                    latch.countDown();
                }
        );
        authServiceMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(latch);

        return isAuthenticated.get();
    }

}
