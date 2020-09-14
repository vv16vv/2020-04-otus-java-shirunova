package ru.otus.vsh.hw16.messagesystem.common;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.otus.vsh.hw16.messagesystem.client.CallbackRegistry;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class CallbackCallRequestHandler<T extends MessageData, R extends MessageData> implements RequestHandler<T, R>{
    private final CallbackRegistry callbackRegistry;

    @Override
    public Optional<Message<R>> handle(Message<T> msg) {
        try {
            val callback = callbackRegistry.getAndRemove(msg.getCallbackId());
            if (callback != null) {
                callback.accept(msg.getBody());
            } else {
                log.error("callback for Id:{} not found", msg.getCallbackId());
            }
        } catch (Exception ex) {
            log.error("msg:{}", msg, ex);
        }
        return Optional.empty();
    }
}
