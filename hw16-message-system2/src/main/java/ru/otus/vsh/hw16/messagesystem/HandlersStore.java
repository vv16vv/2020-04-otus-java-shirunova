package ru.otus.vsh.hw16.messagesystem;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.messagesystem.common.RequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

public interface HandlersStore {
    RequestHandler<? extends MessageData, ? extends MessageData> getHandlerByType(MessageType messageType);

    void addHandler(MessageType messageType, RequestHandler<? extends MessageData, ? extends MessageData> handler);

    boolean isEmpty();
}
