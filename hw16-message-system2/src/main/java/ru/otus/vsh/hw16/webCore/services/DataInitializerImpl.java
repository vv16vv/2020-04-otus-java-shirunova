package ru.otus.vsh.hw16.webCore.services;

import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.dbService.DBServicePlayer;
import ru.otus.vsh.hw16.domain.model.Player;

@Component
public class DataInitializerImpl implements DataInitializer {
    private final DBServicePlayer dbServicePlayer;

    public DataInitializerImpl(DBServicePlayer dbServicePlayer) {
        this.dbServicePlayer = dbServicePlayer;

        createInitialData();
    }

    @Override
    public void createInitialData() {
        var user1 = Player.builder()
                .login("vitkus")
                .name("Виктория")
                .password("12345")
                .get();
        var user2 = Player.builder()
                .login("sevantius")
                .name("Всеволод")
                .password("11111")
                .get();
        var user3 = Player.builder()
                .login("koshir")
                .name("Константин")
                .password("24680")
                .get();

        dbServicePlayer.saveObject(user1);
        dbServicePlayer.saveObject(user2);
        dbServicePlayer.saveObject(user3);

    }
}
