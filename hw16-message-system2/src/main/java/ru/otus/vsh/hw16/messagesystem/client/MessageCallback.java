package ru.otus.vsh.hw16.messagesystem.client;

import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import java.util.function.Consumer;

public interface MessageCallback<T extends MessageData> extends Consumer<T> {
}
