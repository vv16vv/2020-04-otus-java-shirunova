package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class NewPlayerData implements MessageData {
    String login;
    String password;
    String name;
}
