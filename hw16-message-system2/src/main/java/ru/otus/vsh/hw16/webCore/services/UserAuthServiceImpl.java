package ru.otus.vsh.hw16.webCore.services;


import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;

@Component
public class UserAuthServiceImpl implements UserAuthService {

    private final DBServicePlayer dbServicePlayer;

    public UserAuthServiceImpl(DBServicePlayer dbServicePlayer) {
        this.dbServicePlayer = dbServicePlayer;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbServicePlayer.findByLogin(login)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}
