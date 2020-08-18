package ru.otus.vsh.hw16.messagesystem.client;

import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

public interface MsClient {

    boolean sendMessage(Message msg);

    void handle(Message msg);

    String getName();

    <T extends MessageData> Message produceMessage(String to, T data, MessageType msgType, MessageCallback<T> callback);
}
