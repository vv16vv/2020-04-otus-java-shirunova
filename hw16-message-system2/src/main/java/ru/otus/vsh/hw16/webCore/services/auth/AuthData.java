package ru.otus.vsh.hw16.webCore.services.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Data
@NoArgsConstructor
public class AuthData implements MessageData {
    private String login = "vitkus";
    private String password = "12345";
}
