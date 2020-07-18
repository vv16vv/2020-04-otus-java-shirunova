package ru.otus.vsh.hw14.webCore.servlets.page;

import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.webCore.server.Routes;
import ru.otus.vsh.hw14.webCore.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PageNewUserServlet extends HttpServlet {
    public static final String TEMPLATE_ROLES = "roles";
    public static final String TEMPLATE_ROUTE = "route";
    private static final String USER_PAGE_TEMPLATE = "new-user.html";
    private final DBServiceRole dbServiceRole;
    private final TemplateProcessor templateProcessor;

    public PageNewUserServlet(TemplateProcessor templateProcessor, DBServiceRole dbServiceRole) {
        this.templateProcessor = templateProcessor;
        this.dbServiceRole = dbServiceRole;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ROLES, dbServiceRole.findAll());
        paramsMap.put(TEMPLATE_ROUTE, Routes.apiUser);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, paramsMap));
    }

}
