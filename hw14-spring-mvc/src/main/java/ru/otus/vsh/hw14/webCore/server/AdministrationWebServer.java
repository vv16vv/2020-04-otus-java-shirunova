package ru.otus.vsh.hw14.webCore.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.webCore.filters.AuthorizationFilter;
import ru.otus.vsh.hw14.webCore.helpers.FileSystemHelper;
import ru.otus.vsh.hw14.webCore.services.TemplateProcessor;
import ru.otus.vsh.hw14.webCore.services.UserAuthService;
import ru.otus.vsh.hw14.webCore.servlets.api.ApiLoginServlet;
import ru.otus.vsh.hw14.webCore.servlets.api.ApiRoleServlet;
import ru.otus.vsh.hw14.webCore.servlets.api.ApiUserServlet;
import ru.otus.vsh.hw14.webCore.servlets.page.PageActionsServlet;
import ru.otus.vsh.hw14.webCore.servlets.page.PageNewUserServlet;
import ru.otus.vsh.hw14.webCore.servlets.page.PageUsersServlet;

import java.util.Arrays;

public class AdministrationWebServer implements WebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";
    protected final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;
    private final DBServiceRole dbServiceRole;
    private final Server server;
    private final UserAuthService userAuthService;
    private final Gson gson;

    public AdministrationWebServer(int port, DBServiceUser dbServiceUser, DBServiceRole dbServiceRole, UserAuthService userAuthService, TemplateProcessor templateProcessor, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.dbServiceRole = dbServiceRole;
        this.templateProcessor = templateProcessor;
        this.userAuthService = userAuthService;
        this.gson = gson;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private void initContext() {
        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler,
                Routes.apiUser,
                Routes.apiRole,
                Routes.pageUsers,
                Routes.pageNewUser,
                Routes.pageActions
        ));

        server.setHandler(handlers);
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new ApiLoginServlet(userAuthService)), Routes.apiLogin);
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new ApiUserServlet(dbServiceUser, dbServiceRole, gson)), Routes.apiUser);
        servletContextHandler.addServlet(new ServletHolder(new ApiRoleServlet(dbServiceRole, gson)), Routes.apiRole);

        servletContextHandler.addServlet(new ServletHolder(new PageActionsServlet(templateProcessor)), Routes.pageActions);
        servletContextHandler.addServlet(new ServletHolder(new PageUsersServlet(templateProcessor, dbServiceUser)), Routes.pageUsers);
        servletContextHandler.addServlet(new ServletHolder(new PageNewUserServlet(templateProcessor, dbServiceRole)), Routes.pageNewUser);
        return servletContextHandler;
    }
}
