package ru.otus.vsh.hw12.webCore.servlets.page;

import ru.otus.vsh.hw12.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw12.webCore.server.Routes;
import ru.otus.vsh.hw12.webCore.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PageUsersServlet extends HttpServlet {

    private static final String TEMPLATE_USERS = "users";
    private static final String TEMPLATE_ACTIONS = "actions";
    private static final String USERS_PAGE_TEMPLATE = "users.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public PageUsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_USERS, dbServiceUser.findAll());
        paramsMap.put(TEMPLATE_ACTIONS, Routes.pageActions);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
