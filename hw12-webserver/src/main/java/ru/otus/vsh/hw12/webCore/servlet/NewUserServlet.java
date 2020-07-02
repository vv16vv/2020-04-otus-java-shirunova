package ru.otus.vsh.hw12.webCore.servlet;

import ru.otus.vsh.hw12.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw12.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw12.dbCore.model.Address;
import ru.otus.vsh.hw12.dbCore.model.Phone;
import ru.otus.vsh.hw12.dbCore.model.User;
import ru.otus.vsh.hw12.webCore.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewUserServlet extends HttpServlet {
    public static final String TEMPLATE_ROLES = "roles";
    private static final String USER_PAGE_TEMPLATE = "new-user.html";
    private final DBServiceUser dbServiceUser;
    private final DBServiceRole dbServiceRole;
    private final TemplateProcessor templateProcessor;

    public NewUserServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser, DBServiceRole dbServiceRole) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
        this.dbServiceRole = dbServiceRole;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ROLES, dbServiceRole.findAll());
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USER_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var address = new Address(0, req.getParameter("address"));
        var role = dbServiceRole.getObject(Long.valueOf(req.getParameter("role"))).orElseThrow();
        var user = new User(0,
                req.getParameter("login"),
                req.getParameter("name"),
                req.getParameter("password1"),
                address,
                role,
                new ArrayList<>());
        user.addPhone(new Phone(0, req.getParameter("phone1")));
        var phone2 = req.getParameter("phone2");
        if (!phone2.isEmpty()) user.addPhone(new Phone(0, phone2));
        dbServiceUser.saveObject(user);
        resp.sendRedirect("/actions/");
    }
}
