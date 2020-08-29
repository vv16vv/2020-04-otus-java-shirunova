package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
@AllArgsConstructor
public class NewSessionData implements MessageData {
    String login;
    String sessionId;
}
