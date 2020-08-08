package ru.otus.vsh.hw12.webCore.servlets.api;

import com.google.gson.Gson;
import ru.otus.vsh.hw12.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw12.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw12.dbCore.model.Address;
import ru.otus.vsh.hw12.dbCore.model.Phone;
import ru.otus.vsh.hw12.dbCore.model.User;
import ru.otus.vsh.hw12.webCore.server.Routes;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class ApiUserServlet extends HttpServlet {

    private final DBServiceUser dbServiceUser;
    private final DBServiceRole dbServiceRole;
    private final Gson gson;

    public ApiUserServlet(DBServiceUser dbServiceUser, DBServiceRole dbServiceRole, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.dbServiceRole = dbServiceRole;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(dbServiceUser.findAll()));
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
        resp.sendRedirect(Routes.pageActions);
    }

}
