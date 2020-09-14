package ru.otus.vsh.hw16.dbCore.messageSystemClient.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Data
@NoArgsConstructor
public class NewPlayerData implements MessageData {
    String login;
    String password;
    String name;
}
