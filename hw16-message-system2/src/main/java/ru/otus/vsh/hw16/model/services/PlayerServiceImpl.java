package ru.otus.vsh.hw16.model.services;


import ru.otus.vsh.hw16.model.domain.Player;

public class PlayerServiceImpl implements PlayerService {

    private final IOService ioService;

    public PlayerServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Player getPlayer() {
        ioService.out("Представьтесь пожалуйста");
        String playerName = ioService.readLn("Введите имя: ");
        return Player.builder().name(playerName).get();
    }
}
