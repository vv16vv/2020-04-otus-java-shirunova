package ru.otus.vsh.hw16.webCore.loginPage;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw16.dbCore.messageSystemClient.data.NewSessionData;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.common.Id;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;
import ru.otus.vsh.hw16.webCore.services.auth.AuthData;
import ru.otus.vsh.hw16.webCore.services.auth.AuthReplyData;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
public class LoginPageController {
    private static final String TEMPLATE_LOGIN_FORM = "login";
    private static final String TEMPLATE_LOGIN_SIGNON = "signon";
    private static final String TEMPLATE_LOGIN_PLAYERS = "players";
    private static final String TEMPLATE_LOGIN_DATA = "data";
    private static final String INDEX_PAGE_TEMPLATE = "index.html";

    private final LoginPageControllerMSClient loginPageControllerMSClient;

    @GetMapping(Routes.ROOT)
    public String getLoginPage(Model model) {
        model.addAttribute(TEMPLATE_LOGIN_FORM, Routes.ROOT);
        model.addAttribute(TEMPLATE_LOGIN_SIGNON, Routes.NEW_PLAYER);
        model.addAttribute(TEMPLATE_LOGIN_PLAYERS, Routes.PLAYERS);
        model.addAttribute(TEMPLATE_LOGIN_DATA, new AuthData());
        return INDEX_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.ROOT)
    public RedirectView processLogin(@ModelAttribute AuthData data) {
        AtomicReference<AuthReplyData> answer = new AtomicReference<>(null);
        val authMessage = loginPageControllerMSClient.produceMessage(
                MsClientNames.AUTH_SERVICE.name(),
                data, MessageType.LOGIN,
                replay -> answer.set((AuthReplyData) replay)
        );
        loginPageControllerMSClient.sendMessage(authMessage);

        MessageSystemHelper.waitForAnswer(answer,
                ref -> ref.get() != null);

        val sessionId = new Id().getInnerId();
        val sessionMessage = loginPageControllerMSClient.produceMessage(
                MsClientNames.DATA_BASE.name(),
                new NewSessionData(data.getLogin(),sessionId), MessageType.NEW_SESSION,
                replay -> { }
        );
        loginPageControllerMSClient.sendMessage(sessionMessage);

        if (answer.get().isAccessAllowed()) {
            return new RedirectView(Routes.GAME + "/" + sessionId, true);
        } else {
            return new RedirectView(Routes.ROOT, true);
        }
    }

}
