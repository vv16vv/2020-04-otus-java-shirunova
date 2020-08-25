package ru.otus.vsh.hw16.dbCore.messageSystemClient;

import lombok.Value;
import ru.otus.vsh.hw16.messagesystem.common.MessageData;

@Value
public class NewPlayerReplyData implements MessageData {
    boolean isUserAdded;
}
