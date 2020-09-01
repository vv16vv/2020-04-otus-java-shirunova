package ru.otus.vsh.hw16.dbCore.messageSystemClient.handlers;

import lombok.AllArgsConstructor;
import lombok.val;
import ru.otus.vsh.hw16.dbCore.dbService.DBServiceSession;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.NewSessionData;
import ru.otus.vsh.hw16.messagesystem.common.EmptyMessageData;
import ru.otus.vsh.hw16.messagesystem.common.SimpleReceiveRequestHandler;
import ru.otus.vsh.hw16.messagesystem.message.Message;
import ru.otus.vsh.hw16.domain.model.Session;

import java.util.Optional;

@AllArgsConstructor
public class NewSessionDataHandler extends SimpleReceiveRequestHandler<NewSessionData, EmptyMessageData> {
    DBServiceSession dbServiceSession;

    @Override
    public Optional<Message<EmptyMessageData>> handle(Message<NewSessionData> msg) {
        val newSession = Session.builder()
                .sessionId(msg.getBody().getSessionId())
                .login(msg.getBody().getLogin())
                .id(0)
                .get();
        dbServiceSession.saveObject(newSession);
        return Optional.empty();
    }

}