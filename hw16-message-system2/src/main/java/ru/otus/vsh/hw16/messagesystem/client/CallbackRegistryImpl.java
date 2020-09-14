package ru.otus.vsh.hw16.messagesystem.client;

import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallbackRegistryImpl implements CallbackRegistry {

    private final Map<CallbackId, MessageCallback<? extends MessageData>> callbackRegistry = new ConcurrentHashMap<>();

    @Override
    public <T extends MessageData> void put(CallbackId id, MessageCallback<T> callback) {
        callbackRegistry.put(id, callback);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends MessageData> MessageCallback<T> getAndRemove(CallbackId id) {
        return (MessageCallback<T>) callbackRegistry.remove(id);
    }
}
