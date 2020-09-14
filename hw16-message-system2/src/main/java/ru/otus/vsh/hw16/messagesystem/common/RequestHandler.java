package ru.otus.vsh.hw16.messagesystem.common;


import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

public interface RequestHandler<T extends MessageData, R extends MessageData> {
    Optional<Message<R>> handle(Message<T> msg);
}
