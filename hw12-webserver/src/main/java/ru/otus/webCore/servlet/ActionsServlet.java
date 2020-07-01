package ru.otus.webCore.servlet;

import ru.otus.webCore.services.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class ActionsServlet extends HttpServlet {
    private static final String ACTIONS_PAGE_TEMPLATE = "actions.html";

    private final TemplateProcessor templateProcessor;

    public ActionsServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ACTIONS_PAGE_TEMPLATE, Collections.emptyMap()));
    }

}
