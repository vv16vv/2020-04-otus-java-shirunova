package ru.otus.vsh.hw14.webCore.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.webCore.server.Routes;

import java.util.List;

@RestController
public class ApiRoleController {

    private final DBServiceRole dbServiceRole;

    public ApiRoleController(DBServiceRole dbServiceRole) {
        this.dbServiceRole = dbServiceRole;
    }

    @GetMapping(Routes.API_ROLE)
    public List<Role> getAllRoles() {
        return dbServiceRole.findAll();
    }

}
