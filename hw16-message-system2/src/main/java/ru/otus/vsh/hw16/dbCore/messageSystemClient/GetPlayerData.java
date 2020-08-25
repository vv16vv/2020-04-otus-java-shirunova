package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class GetPlayerData implements MessageData {
    String login;
}
