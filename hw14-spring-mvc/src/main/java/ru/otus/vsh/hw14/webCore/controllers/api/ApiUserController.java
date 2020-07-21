package ru.otus.vsh.hw14.webCore.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.dbCore.model.Address;
import ru.otus.vsh.hw14.dbCore.model.Phone;
import ru.otus.vsh.hw14.dbCore.model.Role;
import ru.otus.vsh.hw14.dbCore.model.User;
import ru.otus.vsh.hw14.webCore.controllers.dataClasses.UserData;
import ru.otus.vsh.hw14.webCore.server.Routes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiUserController {

    private final DBServiceUser dbServiceUser;
    private final DBServiceRole dbServiceRole;

    public ApiUserController(DBServiceUser dbServiceUser, DBServiceRole dbServiceRole) {
        this.dbServiceUser = dbServiceUser;
        this.dbServiceRole = dbServiceRole;
    }

    @GetMapping(Routes.API_USER)
    public List<User> getAllUsers() {
        return dbServiceUser.findAll();
    }

    @PostMapping(Routes.API_USER)
    public RedirectView addUser(@ModelAttribute UserData userData) {
        var address = new Address(0, userData.getAddress());
        var roles = dbServiceRole.findAll().stream()
                .filter(role -> role.getName().equals(userData.getRole()))
                .collect(Collectors.toList());
        Role role;
        if (roles.isEmpty()) {
            role = new Role(0, userData.getRole());
            dbServiceRole.saveObject(role);
        } else {
            role = roles.get(0);
        }
        var user = new User(0,
                userData.getLogin(),
                userData.getName(),
                userData.getPassword(),
                address,
                role,
                new ArrayList<>());
        user.addPhone(new Phone(0, userData.getPhone1()));
        var phone2 = userData.getPhone2();
        if (!phone2.isEmpty()) user.addPhone(new Phone(0, phone2));
        dbServiceUser.saveObject(user);
        return new RedirectView(Routes.PAGE_USERS, true);
    }

}
