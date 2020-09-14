package ru.otus.vsh.hw16.messagesystem.client;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

public interface MsClient {

    <T extends MessageData> boolean sendMessage(Message<T> msg);

    <T extends MessageData> void handle(Message<T> msg);

    String getName();

    <T extends MessageData, R extends MessageData> Message<T> produceMessage(String to, T data, MessageType msgType, MessageCallback<R> callback);

}
