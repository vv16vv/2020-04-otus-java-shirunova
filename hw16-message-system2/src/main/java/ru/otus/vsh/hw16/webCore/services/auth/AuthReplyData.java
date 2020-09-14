package ru.otus.vsh.hw16.webCore.services.auth;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class AuthReplyData implements MessageData {
    boolean isAccessAllowed;
}
