package ru.otus.vsh.hw16.domain.messageSystemClient;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.domain.messageSystemClient.handlers.EquationPreparerDataHandler;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.HandlersStoreImpl;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

@Component
public class EquationPreparerMSClientInitializerImpl implements EquationPreparerMSClientInitializer {
    @Override
    @Bean
    public EquationPreparerMSClient equationPreparerMSClient(MessageSystem messageSystem,
                                                             CallbackRegistry callbackRegistry) {
        HandlersStore store = new HandlersStoreImpl();
        store.addHandler(MessageType.NEW_GAME, new EquationPreparerDataHandler());
        val equationPreparerMSClient = new EquationPreparerMSClient(messageSystem, store, callbackRegistry);
        messageSystem.addClient(equationPreparerMSClient);

        return equationPreparerMSClient;
    }
}
