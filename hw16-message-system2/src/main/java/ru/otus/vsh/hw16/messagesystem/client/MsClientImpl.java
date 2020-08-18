package ru.otus.vsh.hw16.messagesystem.client;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import ru.otus.vsh.hw16.messagesystem.HandlersStore;
import ru.otus.vsh.hw16.messagesystem.MessageSystem;
import ru.otus.vsh.hw16.messagesystem.RequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;

@Slf4j
@EqualsAndHashCode(of = {"name"})
@AllArgsConstructor
public class MsClientImpl implements MsClient {

    private final String name;
    private final MessageSystem messageSystem;
    private final HandlersStore handlersStore;
    private final CallbackRegistry callbackRegistry;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean sendMessage(Message msg) {
        boolean result = messageSystem.newMessage(msg);
        if (!result) {
            log.error("the last message was rejected: {}", msg);
        }
        return result;
    }

    @SuppressWarnings("all")
    @Override
    public void handle(Message msg) {
        log.info("new message:{}", msg);
        try {
            RequestHandler requestHandler = handlersStore.getHandlerByType(msg.getType());
            if (requestHandler != null) {
                requestHandler.handle(msg).ifPresent(message -> sendMessage((Message) message));
            } else {
                log.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            log.error("msg:{}", msg, ex);
        }
    }

    @Override
    public <T extends MessageData> Message produceMessage(String to,
                                                          T data,
                                                          MessageType msgType,
                                                          MessageCallback<T> callback) {
        Message message = Message.builder()
                .from(name)
                .to(to)
                .body(data)
                .type(msgType)
                .callbackId(new CallbackId())
                .get();
        callbackRegistry.put(message.getCallbackId(), callback);
        return message;
    }

}
