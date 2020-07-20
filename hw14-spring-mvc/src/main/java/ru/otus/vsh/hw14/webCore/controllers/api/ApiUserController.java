package ru.otus.vsh.hw14.webCore.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceRole;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;
import ru.otus.vsh.hw14.dbCore.model.User;
import ru.otus.vsh.hw14.webCore.server.Routes;

import java.util.List;

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
    public RedirectView addUser(@ModelAttribute User user) {
//        var address = new Address(0, req.getParameter("address"));
//        var role = dbServiceRole.getObject(Long.valueOf(req.getParameter("role"))).orElseThrow();
//        var user = new User(0,
//                req.getParameter("login"),
//                req.getParameter("name"),
//                req.getParameter("password1"),
//                address,
//                role,
//                new ArrayList<>());
//        user.addPhone(new Phone(0, req.getParameter("phone1")));
//        var phone2 = req.getParameter("phone2");
//        if (!phone2.isEmpty()) user.addPhone(new Phone(0, phone2));
        dbServiceUser.saveObject(user);
        return new RedirectView(Routes.PAGE_ACTIONS, true);
    }

}
