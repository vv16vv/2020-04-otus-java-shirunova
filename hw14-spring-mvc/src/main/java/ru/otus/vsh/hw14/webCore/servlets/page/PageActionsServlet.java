package ru.otus.vsh.hw14.webCore.servlets.page;

import ru.otus.vsh.hw14.webCore.server.Routes;
import ru.otus.vsh.hw14.webCore.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PageActionsServlet extends HttpServlet {
    public static final String TEMPLATE_NEW_USER = "newUser";
    public static final String TEMPLATE_ALL_USERS = "allUsers";
    private static final String ACTIONS_PAGE_TEMPLATE = "actions.html";

    private final TemplateProcessor templateProcessor;

    public PageActionsServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_NEW_USER, Routes.pageNewUser);
        paramsMap.put(TEMPLATE_ALL_USERS, Routes.pageUsers);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ACTIONS_PAGE_TEMPLATE, paramsMap));
    }

}
