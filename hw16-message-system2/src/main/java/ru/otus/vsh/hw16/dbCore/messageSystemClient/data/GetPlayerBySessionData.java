package ru.otus.vsh.hw16.dbCore.messageSystemClient.data;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class GetPlayerBySessionData implements MessageData {
    String sessionId;
}
