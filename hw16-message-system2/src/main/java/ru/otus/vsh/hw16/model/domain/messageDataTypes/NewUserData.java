package ru.otus.vsh.hw16.model.domain.messageDataTypes;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class NewUserData implements MessageData {
    String login;
    String password;
    String name;
}
