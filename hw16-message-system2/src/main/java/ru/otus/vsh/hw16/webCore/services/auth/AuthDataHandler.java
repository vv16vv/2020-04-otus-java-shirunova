package ru.otus.vsh.hw16.webCore.services.auth;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.messagesystem.common.ResponseProduceRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;

import java.util.Optional;

/**
 * Исполняется клиентом [AuthServiceMSClient] при обработке сообщения LOGIN
 * Идет обращение к сервису [AuthService]
 * А в нем идет запрос GET_PLAYER к [DataBaseMSClient]
 */
@AllArgsConstructor
public class AuthDataHandler implements ResponseProduceRequestHandler<AuthData, AuthReplyData> {
    private final AuthService authService;

    @Override
    public Optional<Message<AuthReplyData>> handle(Message<AuthData> msg) {
        val loginReplyData = new AuthReplyData(authService.authenticate(msg.getBody().getLogin(), msg.getBody().getPassword()));
        return Optional.of(Message.buildReplyMessage(msg, loginReplyData));
    }
}
