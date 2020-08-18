package ru.otus.vsh.hw16.messagesystem.client;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CallbackRegistryImpl implements CallbackRegistry {

    private final Map<CallbackId, MessageCallback<? extends MessageData>> callbackRegistry = new ConcurrentHashMap<>();

    @Override
    public void put(CallbackId id, MessageCallback<? extends MessageData> callback) {
        callbackRegistry.put(id, callback);
    }

    @Override
    public MessageCallback<? extends MessageData> getAndRemove(CallbackId id) {
        return callbackRegistry.remove(id);
    }
}
