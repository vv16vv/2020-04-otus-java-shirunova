package ru.otus.vsh.hw16.webCore.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistryImpl;

@Component
public class CallbackRegistryInitializerImpl implements CallbackRegistryInitializer {
    @Override
    @Bean
    public CallbackRegistry callbackRegistry() {
        return new CallbackRegistryImpl();
    }
}
