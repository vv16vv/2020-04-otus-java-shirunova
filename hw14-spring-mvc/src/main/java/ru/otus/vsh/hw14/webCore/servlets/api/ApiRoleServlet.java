package ru.otus.vsh.hw14.webCore.servlets.api;

import com.google.gson.Gson;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ApiRoleServlet extends HttpServlet {

    private final DBServiceRole dbServiceRole;
    private final Gson gson;

    public ApiRoleServlet(DBServiceRole dbServiceRole, Gson gson) {
        this.dbServiceRole = dbServiceRole;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(dbServiceRole.findAll()));
    }

}
