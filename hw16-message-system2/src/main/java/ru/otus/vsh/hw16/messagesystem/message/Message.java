package ru.otus.vsh.hw16.messagesystem.message;

import lombok.*;
import ru.otus.vsh.hw16.messagesystem.client.CallbackId;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

import java.io.Serializable;

@Value
@Builder(buildMethodName = "get", builderClassName = "Builder")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter
    private static final Message VOID_MESSAGE =
            new Message.Builder()
                    .from("")
                    .to("")
                    .type(MessageType.VOID)
                    .get();

    @With
    String from;
    @With
    String to;

    MessageType type;

    @lombok.Builder.Default
    MessageId id = new MessageId();
    @With
    MessageId sourceMessageId;

    @With
    MessageData body;

    CallbackId callbackId;

    public static <T extends MessageData> Message buildReplyMessage(Message message, T data) {
        return message
                .withFrom(message.to)
                .withTo(message.from)
                .withSourceMessageId(message.id)
                .withBody(data);
    }
}
