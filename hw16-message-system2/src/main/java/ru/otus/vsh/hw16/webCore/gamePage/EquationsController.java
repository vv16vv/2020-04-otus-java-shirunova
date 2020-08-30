package ru.otus.vsh.hw16.webCore.gamePage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.otus.vsh.hw16.domain.messageSystemClient.data.EquationMessage;
import ru.otus.vsh.hw16.webCore.server.Routes;

@Slf4j
@Controller
public class EquationsController {

    @MessageMapping("/message.{sessionId}")
    @SendTo(Routes.API_TOPIC + "/response.{sessionId}")
    public EquationMessage getMessage(@DestinationVariable String sessionId, EquationMessage equationMessage) {
        log.info("got equationMessage:{}, sessionId:{}", equationMessage, sessionId);
        return new EquationMessage(HtmlUtils.htmlEscape(equationMessage.getMessageStr()));
    }

}
