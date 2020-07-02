package ru.otus.vsh.hw12.webCore.servlet;

import ru.otus.vsh.hw12.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw12.webCore.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;


public class NewUserServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;
    private static final String USER_PAGE_TEMPLATE = "new-user.html";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public NewUserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
//        session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
        resp.sendRedirect("/actions");
    }
}
