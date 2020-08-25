package ru.otus.vsh.hw16.webCore.controllers.page;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw16.messagesystem.MessageSystemHelper;
import ru.otus.vsh.hw16.messagesystem.message.MessageType;
import ru.otus.vsh.hw16.webCore.services.auth.AuthData;
import ru.otus.vsh.hw16.webCore.services.auth.AuthReplyData;
import ru.otus.vsh.hw16.webCore.msClients.LoginControllerMSClient;
import ru.otus.vsh.hw16.webCore.server.Routes;
import ru.otus.vsh.hw16.webCore.services.MsClientNames;

import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
public class PageLoginController {
    private static final String TEMPLATE_LOGIN_FORM = "login";
    private static final String TEMPLATE_LOGIN_SIGNON = "signon";
    private static final String TEMPLATE_LOGIN_DATA = "data";
    private static final String INDEX_PAGE_TEMPLATE = "index.html";

    private final LoginControllerMSClient loginControllerMSClient;

    @GetMapping(Routes.ROOT)
    public String getLoginPage(Model model) {
        model.addAttribute(TEMPLATE_LOGIN_FORM, Routes.ROOT);
        model.addAttribute(TEMPLATE_LOGIN_SIGNON, Routes.NEW_PLAYER);
        model.addAttribute(TEMPLATE_LOGIN_DATA, new AuthData());
        return INDEX_PAGE_TEMPLATE;
    }

    @PostMapping(Routes.ROOT)
    public RedirectView processLogin(@ModelAttribute AuthData data) {
        AtomicReference<AuthReplyData> answer = new AtomicReference<>(null);
        val message = loginControllerMSClient.produceMessage(
                MsClientNames.AUTH_SERVICE.name(),
                data, MessageType.LOGIN,
                replay -> answer.set((AuthReplyData) replay)
        );
        loginControllerMSClient.sendMessage(message);

        MessageSystemHelper.waitForAnswer(answer,
                ref -> ref.get() != null);

        if (answer.get().isAccessAllowed()) {
            return new RedirectView(Routes.GAME, true);
        } else {
            return new RedirectView(Routes.ROOT, true);
        }
    }

}
