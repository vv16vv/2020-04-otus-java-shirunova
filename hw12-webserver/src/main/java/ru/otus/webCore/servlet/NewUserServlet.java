package ru.otus.webCore.servlet;

import com.google.gson.Gson;
import ru.otus.dbCore.dao.UserDao;
import ru.otus.webCore.services.TemplateProcessor;

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

    private final UserDao userDao;
    private final TemplateProcessor templateProcessor;

    public NewUserServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, Collections.emptyMap()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter(PARAM_LOGIN);
//        String password = req.getParameter(PARAM_PASSWORD);

        HttpSession session = req.getSession();
//        session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
        resp.sendRedirect("/actions");
    }
}
