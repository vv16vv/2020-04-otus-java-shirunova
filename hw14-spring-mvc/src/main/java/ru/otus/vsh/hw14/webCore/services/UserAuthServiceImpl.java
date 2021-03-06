package ru.otus.vsh.hw14.webCore.services;


import org.springframework.stereotype.Component;
import ru.otus.vsh.hw14.dbCore.dbService.DBServiceUser;

@Component
public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUser dbServiceUser;

    public UserAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServiceUser.findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
