package ru.otus.vsh.hw16.messagesystem.client;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;

public interface CallbackRegistry {
    void put(CallbackId id, MessageCallback<? extends MessageData> callback);

    MessageCallback<? extends MessageData> getAndRemove(CallbackId id);
}
