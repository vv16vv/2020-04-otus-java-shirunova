package ru.otus.vsh.hw16.webCore.loginPage;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.common.CallbackReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.webCore.services.auth.AuthData;
import ru.otus.vsh.hw16.webCore.services.auth.AuthReplyData;

@Component
public class LoginPageMSClientInitializerImpl implements LoginPageMSClientInitializer {

    @Override
    @Bean
    public LoginPageControllerMSClient loginControllerMSClient(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.LOGIN, new CallbackReceiveRequestHandler<AuthData, AuthReplyData>(callbackRegistry));
        val loginControllerMSClient = new LoginPageControllerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(loginControllerMSClient);

        return loginControllerMSClient;
    }

}
