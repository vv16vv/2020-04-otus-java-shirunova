package ru.otus.vsh.hw16.messagesystem;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlersStoreImpl implements HandlersStore {
    private final Map<MessageType, RequestHandler<? extends MessageData>> handlers = new ConcurrentHashMap<>();

    @Override
    public RequestHandler<? extends MessageData> getHandlerByType(MessageType messageType) {
        return handlers.get(messageType);
    }

    @Override
    public void addHandler(MessageType messageType, RequestHandler<? extends MessageData> handler) {
        handlers.put(messageType, handler);
    }


}
