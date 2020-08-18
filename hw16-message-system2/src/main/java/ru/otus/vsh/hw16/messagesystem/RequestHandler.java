package ru.otus.vsh.hw16.messagesystem;


import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.io.Serializable;
import java.util.Optional;

public interface RequestHandler<T extends Serializable> {
    Optional<Message> handle(Message msg);
}
