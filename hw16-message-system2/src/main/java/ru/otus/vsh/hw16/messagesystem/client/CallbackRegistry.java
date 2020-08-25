package ru.otus.vsh.hw16.messagesystem.client;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;

public interface CallbackRegistry {
    <T extends MessageData> void put(CallbackId id, MessageCallback<T> callback);

    <T extends MessageData> MessageCallback<T> getAndRemove(CallbackId id);
}
